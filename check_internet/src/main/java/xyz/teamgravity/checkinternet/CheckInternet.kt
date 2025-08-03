package xyz.teamgravity.checkinternet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class CheckInternet(
    application: Context
) {

    private companion object {
        const val TAG = "CheckInternet"
        const val HOST_NAME = "8.8.8.8"
        const val PORT = 53
        const val TIMEOUT = 1_500
    }

    private val _status = MutableStateFlow(Status.Initial)
    val status: StateFlow<Status> = _status.asStateFlow()

    private val manager: ConnectivityManager? = application.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    private val scope: CoroutineScope = MainScope()

    private suspend fun checkInternet(factory: SocketFactory): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                factory.createSocket()?.use { it.connect(InetSocketAddress(HOST_NAME, PORT), TIMEOUT) } ?: return@withContext false
                return@withContext true
            } catch (e: IOException) {
                return@withContext false
            }
        }
    }

    private fun logError(message: String) {
        Log.e(TAG, message)
    }

    private val callback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            val manager = manager
            if (manager == null) {
                logError("onAvailable(): ConnectivityManager is null! Aborted the operation.")
                return
            }

            scope.launch {
                if (manager.getNetworkCapabilities(network)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
                    val hasInternet = checkInternet(network.socketFactory)
                    _status.emit(if (hasInternet) Status.Connected else Status.NotConnected)
                } else {
                    _status.emit(Status.NotConnected)
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            scope.launch {
                _status.emit(Status.NotConnected)
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun check(listener: (connected: Boolean) -> Unit) {
        scope.launch {
            listener(check())
        }
    }

    suspend fun check(): Boolean {
        return checkInternet(SocketFactory.getDefault())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun startObservingConnection() {
        val manager = manager
        if (manager == null) {
            logError("startObservingConnection(): ConnectivityManager is null! Aborted the operation.")
            return
        }

        scope.launch {
            if (status.value != Status.Initial) return@launch
            _status.emit(if (check()) Status.Connected else Status.NotConnected)
            manager.registerDefaultNetworkCallback(callback)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun stopObservingConnection() {
        val manager = manager
        if (manager == null) {
            logError("stopObservingConnection(): ConnectivityManager is null! Aborted the operation.")
            return
        }

        scope.launch {
            manager.unregisterNetworkCallback(callback)
            _status.emit(Status.Initial)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Misc
    ///////////////////////////////////////////////////////////////////////////

    enum class Status {
        Initial,
        Connected,
        NotConnected;
    }
}