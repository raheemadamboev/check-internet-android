package xyz.teamgravity.checkinternetdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        onCheck()
    }

    private fun onCheck() {
        binding.apply {
            checkB.setOnClickListener {

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
}