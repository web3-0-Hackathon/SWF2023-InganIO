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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import web3_hackathon.humanio.data.api.human_io.HumanIONftApi
import web3_hackathon.humanio.data.model.CreateNftItemRequest
import web3_hackathon.humanio.data.model.CreateNftItemResponse
import web3_hackathon.humanio.data.model.DeployNftContractResponse
import web3_hackathon.humanio.data.model.GetNftContractDeploymentInfoResponse
import web3_hackathon.humanio.databinding.DialogAskRegistrationBinding
import web3_hackathon.humanio.di.NetworkModule

class AskRegistrationDialog : DialogFragment() {
    private var _binding: DialogAskRegistrationBinding? = null
    private val binding: DialogAskRegistrationBinding
        get() = _binding!!

    private val nftApi: HumanIONftApi? = NetworkModule
        .provideHumanIOApi(NetworkModule.provideOkHttpClient())
        .create(HumanIONftApi::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogAskRegistrationBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        setupView()
        setupListeners()

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

            getDataToServer(requireArguments().getString(UUID))
            // dialog -> activity
            setFragmentResult(AskRegistrationDialog.TAG, Bundle().apply {
                putString(AskRegistrationDialog.RESULT_ACTION, AskRegistrationDialog.ACTION_REGISTER)
            })

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val RESULT_ACTION = "result_action"
        const val ACTION_REGISTER = "action_register"
        const val UUID = "uuid"

        fun newInstance(uuid: String?): AskRegistrationDialog =
            AskRegistrationDialog().apply {
                arguments = Bundle().apply {
                    putString(UUID, uuid)
                }
            }

        const val TAG = "AskRegistrationDialog"
    }

    private fun getDataToServer(uuid: String?) {
        val call = nftApi?.getNftContractDeploymentInfo(uuid)

        call?.enqueue(object : Callback<GetNftContractDeploymentInfoResponse> {
            override fun onResponse(
                call: Call<GetNftContractDeploymentInfoResponse>,
                response: Response<GetNftContractDeploymentInfoResponse>
            ) {
                if (response.isSuccessful) {
                    Log.i("getDataToServer", "onResponse: $uuid")
                    if (response.body()?.statusCode == "SENT") {
                        val call = nftApi?.createNftItem(
                            CreateNftItemRequest(response.body()?.conSeq,
                                response.body()?.contractAddress)
                        )

                        call?.enqueue(object : Callback<CreateNftItemResponse> {
                            override fun onResponse(
                                call: Call<CreateNftItemResponse>,
                                response: Response<CreateNftItemResponse>
                            ) {
                                val intent = MyTicketIssueFragment.newInstance()
                                val transaction = activity?.supportFragmentManager?.beginTransaction()
                                transaction?.add(R.id.fl_container, intent)?.commit()
                            }

                            override fun onFailure(
                                call: Call<CreateNftItemResponse>,
                                t: Throwable
                            ) {
                                t.printStackTrace()
                            }

                        })
                    } else {
                        Toast.makeText(requireContext(), "NFT 발행 대기중입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<GetNftContractDeploymentInfoResponse>, t: Throwable) {
                // Handle the case when the API call failed
                t.printStackTrace()
                Log.e("DeployNftContractRequest", "API call failed with exception: ${t.message}")
            }
        })

    }

    private fun sendDataToServer() {

    }
}