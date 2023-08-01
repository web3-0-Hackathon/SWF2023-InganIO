package web3_hackathon.humanio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import web3_hackathon.humanio.databinding.ActivityEventDetail02Binding

@AndroidEntryPoint
class EventDetail02Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetail02Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetail02Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListeners()
    }

    private fun initView() {

    }

    private fun initListeners() {
        binding.ivArrowBack.setOnClickListener {
            finish()
        }

        binding.ivSuggestPrice.setOnClickListener {

        }
    }
}