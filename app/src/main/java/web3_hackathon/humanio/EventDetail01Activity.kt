package web3_hackathon.humanio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import web3_hackathon.humanio.data.api.octet.ChildAddressApi
import web3_hackathon.humanio.data.model.CreateChildAddressRequest
import web3_hackathon.humanio.data.model.CreateChildAddressResponse
import web3_hackathon.humanio.data.model.FetchChildAddressListResponse
import web3_hackathon.humanio.databinding.ActivityEventDetail01Binding
import web3_hackathon.humanio.di.NetworkModule

@AndroidEntryPoint
class EventDetail01Activity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetail01Binding
    private val childAddressApi: ChildAddressApi? = NetworkModule.provideHumanIOApi(NetworkModule.provideOkHttpClient()).create(ChildAddressApi::class.java)

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
            createChildAddress()
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

    private fun createChildAddress() {
        val call = childAddressApi?.createChildAddress(1059, CreateChildAddressRequest(
            "",
            10,
            "desc",
            "")
        )
        call?.enqueue(object : Callback<CreateChildAddressResponse> {
            override fun onResponse(
                call: Call<CreateChildAddressResponse>,
                response: Response<CreateChildAddressResponse>,
            ) {
                if (response.isSuccessful) {
                    Log.i("createChildAddress", "onResponse: ${response.body()?.address} ${response.body()?.name}")
                }
            }

            override fun onFailure(call: Call<CreateChildAddressResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("createChildAddress", "onFailure: ${t.printStackTrace()}")
            }
        })
    }

    private fun onClickBuyTicketButton() {
//        val askBuyTicketDialog = AskBuyTicketDialog.newInstance()
//        askBuyTicketDialog.show(supportFragmentManager, AskBuyTicketDialog.TAG)
    }
}