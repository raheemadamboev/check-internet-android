package xyz.teamgravity.checkinternetdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import xyz.teamgravity.checkinternetdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        button()
    }

    private fun button() {
        onCheckCallback()
        onCheckSuspend()
    }

    private fun onCheckCallback() {
        binding.apply {
            checkCallbackB.setOnClickListener {

                stateT.text = getString(R.string.checking)

                CheckInternet().check { connected ->
                    if (connected) { // there is internet
                        stateT.text = getString(R.string.internet)
                    } else { // there is no internet
                        stateT.text = getString(R.string.no_internet)
                    }
                }
            }
        }
    }

    private fun onCheckSuspend() {
        binding.apply {
            checkSuspendB.setOnClickListener {
                lifecycleScope.launch {
                    stateT.text = getString(R.string.checking)
                    val connected = CheckInternet().check()
                    stateT.text = getString(if (connected) R.string.internet else R.string.no_internet)
                }
            }
        }
    }
}