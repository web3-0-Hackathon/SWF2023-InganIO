package web3_hackathon.humanio

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import web3_hackathon.humanio.data.api.human_io.ContractItemInfoResponse
import web3_hackathon.humanio.data.api.human_io.GetNftContractItemInfoApi
import web3_hackathon.humanio.data.api.human_io.HumanIONftApi
import web3_hackathon.humanio.data.api.human_io.WithdrawMoneyFromWalletApi
import web3_hackathon.humanio.data.model.human_io.EventInfoRequest
import web3_hackathon.humanio.data.model.human_io.EventInfoResponse
import web3_hackathon.humanio.databinding.DialogAskBuyTicketBinding
import web3_hackathon.humanio.di.NetworkModule

class AskBuyTicketDialog : DialogFragment() {
    private var _binding: DialogAskBuyTicketBinding? = null
    private val binding: DialogAskBuyTicketBinding
        get() = _binding!!

    private val withdrawMoneyFromWalletApi: WithdrawMoneyFromWalletApi? = NetworkModule
        .provideHumanIOApi(NetworkModule.provideOkHttpClient())
        .create(WithdrawMoneyFromWalletApi::class.java)

    private val getNftContractItemInfoApi: GetNftContractItemInfoApi? = NetworkModule
        .provideHumanIOApi(NetworkModule.provideOkHttpClient())
        .create(GetNftContractItemInfoApi::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogAskBuyTicketBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        setupView()
        setupListeners()

        getNftContractItemInfo()

        return binding.root
    }

    private fun setupView() {
        binding.buttonPanel.background = GradientDrawable().apply {
            val radius = resources.getDimension(R.dimen.buy_ticket_dialog_radius)
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, radius, radius, radius, radius)

            val strokeWidth =
                resources.getDimensionPixelSize(R.dimen.buy_ticket_dialog_stroke_width)
            setStroke(strokeWidth, ContextCompat.getColor(requireContext(), R.color.light_gray))
        }
    }

    private fun setupListeners() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            buyTicket()

            // dialog -> activity
            setFragmentResult(TAG, Bundle().apply {
                putString(RESULT_ACTION, ACTION_BUY)
            })
            dismiss()
        }
    }

    var tokenId = ""
    var contractAddress = ""

    private fun getNftContractItemInfo() {
        val call = getNftContractItemInfoApi?.getNftContractItemInfo(1, 1)

        call?.enqueue(object : Callback<ContractItemInfoResponse> {
            override fun onResponse(
                call: Call<ContractItemInfoResponse>,
                response: Response<ContractItemInfoResponse>,
            ) {
                if (response.isSuccessful) {
                    tokenId = response.body()?.tokenId.orEmpty()
                    contractAddress = response.body()?.contractAddress.orEmpty()
                }
            }

            override fun onFailure(call: Call<ContractItemInfoResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun buyTicket() {
        val call = withdrawMoneyFromWalletApi?.withdrawMoneyFromWallet(EventInfoRequest(binding.editId.text.toString(), "tokenId"," ", ""))

        call?.enqueue(object : Callback<EventInfoResponse> {
            override fun onResponse(
                call: Call<EventInfoResponse>,
                response: Response<EventInfoResponse>,
            ) {
                if (response.isSuccessful) {
                    Log.i("buyTicket", "onResponse: ${response.body()?.resultCode}")
                    // 예매 확인/취소 화면으로 이동
                }
            }

            override fun onFailure(call: Call<EventInfoResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val RESULT_ACTION = "result_action"
        const val ACTION_BUY = "action_buy"
        const val NAME = "name"

        fun newInstance(name: String?): AskBuyTicketDialog =
            AskBuyTicketDialog().apply {
                arguments = Bundle().apply {
                    putString(NAME, name)
                }
            }

        const val TAG = "AskBuyTicketDialog"
    }
}