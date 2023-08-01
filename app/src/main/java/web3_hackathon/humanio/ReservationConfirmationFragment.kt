package web3_hackathon.humanio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import web3_hackathon.humanio.R
import web3_hackathon.humanio.databinding.FragmentReservationConfirmationBinding

class ReservationConfirmationFragment : Fragment() {
    private var _binding: FragmentReservationConfirmationBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationConfirmationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgTicketMain.setOnClickListener {
            val intent = QRCodeActivity.getIntent(requireContext())
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() =
            ReservationConfirmationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}