package xyz.teamgravity.checkinternetdemo

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.checkinternet.CheckInternet
import xyz.teamgravity.checkinternetdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val internet: CheckInternet by lazy { CheckInternet(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ui()
        observe()
        button()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) internet.startObservingConnection()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) internet.stopObservingConnection()
    }

    private fun ui() {
        edgeToEdge()
    }

    private fun observe() {
        observeInternet()
    }

    private fun button() {
        onCheckCallback()
        onCheckSuspend()
    }

    private fun edgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val paddings =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime())
            view.setPadding(paddings.left, paddings.top, paddings.right, paddings.bottom)
            return@setOnApplyWindowInsetsListener insets
        }
    }

    private fun observeInternet() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lifecycleScope.launch {
                internet.status.collectLatest { status ->
                    binding.observeStateT.text = getString(R.string.your_observing_internet, status.name)
                }
            }
        }
    }

    private fun onCheckCallback() {
        binding.apply {
            checkCallbackB.setOnClickListener {
                stateT.text = getString(R.string.checking)
                internet.check { connected ->
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
                    val connected = internet.check()
                    stateT.text = getString(if (connected) R.string.internet else R.string.no_internet)
                }
            }
        }
    }
}