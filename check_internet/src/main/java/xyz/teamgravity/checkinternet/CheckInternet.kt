package xyz.teamgravity.checkinternet

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class CheckInternet {

    fun check(listener: (connected: Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            try {
                val socket = Socket()
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
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
}