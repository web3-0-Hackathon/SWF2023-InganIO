package web3_hackathon.humanio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import web3_hackathon.humanio.databinding.ActivityEventDetail01Binding

@AndroidEntryPoint
class EventDetail01Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetail01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventDetail01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.ivArrowBack.setOnClickListener {
            finish()
        }

        binding.ivButtonBuyTicket.setOnClickListener {
            onClickBuyTicketButton()
        }

        supportFragmentManager.setFragmentResultListener(
            AskBuyTicketDialog.TAG,
            this@EventDetail01Activity
        ) { _: String, result: Bundle ->
            val action = result.getString(AskBuyTicketDialog.RESULT_ACTION)
            if (action == AskBuyTicketDialog.ACTION_BUY) {

            }
        }
    }

    private fun onClickBuyTicketButton() {
        val askBuyTicketDialog = AskBuyTicketDialog.newInstance()
        askBuyTicketDialog.show(supportFragmentManager, AskBuyTicketDialog.TAG)
    }
}