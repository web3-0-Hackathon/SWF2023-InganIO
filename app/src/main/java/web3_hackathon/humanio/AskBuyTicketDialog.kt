package web3_hackathon.humanio

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import web3_hackathon.humanio.databinding.DialogAskBuyTicketBinding

class AskBuyTicketDialog : DialogFragment() {
    private var _binding: DialogAskBuyTicketBinding? = null
    private val binding: DialogAskBuyTicketBinding
        get() = _binding!!

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
            // dialog -> activity
            setFragmentResult(TAG, Bundle().apply {
                putString(RESULT_ACTION, ACTION_BUY)
            })
            dismiss()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val RESULT_ACTION = "result_action"
        const val ACTION_BUY = "action_buy"

        fun newInstance(): AskBuyTicketDialog =
            AskBuyTicketDialog().apply {
                arguments = Bundle().apply {

                }
            }

        const val TAG = "AskBuyTicketDialog"
    }
}