package xyz.teamgravity.checkinternet

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CheckInternet {

    companion object {
        private const val HOST_NAME = "8.8.8.8"
        private const val PORT = 53
        private const val TIMEOUT = 1_500
    }

    fun check(listener: (connected: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress(HOST_NAME, PORT), TIMEOUT)
                socket.close()
                withContext(Dispatchers.Main) {
                    listener(true)
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    listener(false)
                }
            }
        }
    }

    suspend fun check(): Boolean {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                try {
                    val socket = Socket()
                    socket.connect(InetSocketAddress(HOST_NAME, PORT), TIMEOUT)
                    socket.close()
                    continuation.resume(true)
                } catch (e: IOException) {
                    continuation.resume(false)
                }
            }
        }
    }
}