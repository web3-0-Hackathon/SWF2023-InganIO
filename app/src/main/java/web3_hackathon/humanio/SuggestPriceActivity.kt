package web3_hackathon.humanio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import web3_hackathon.humanio.databinding.ActivitySuggestPriceBinding

class SuggestPriceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuggestPriceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuggestPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.ivClose.setOnClickListener {
            finish()
        }

        binding.btnAccept.setOnClickListener {
            onClickAcceptButton()
        }
    }

    private fun onClickAcceptButton() {
//        val fragment = supportFragmentManager.fragments.find { it is TicketTransactionDetailFragment }
    }
}