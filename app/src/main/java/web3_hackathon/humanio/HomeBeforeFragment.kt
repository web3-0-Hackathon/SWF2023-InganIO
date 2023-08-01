package web3_hackathon.humanio

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeBeforeFragment : Fragment() {

    private lateinit var btnFloating: FloatingActionButton
    private lateinit var btnRectangle: ImageView
    private lateinit var back: ImageView
    private var isButtonClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_before, container, false)

        back = view.findViewById(R.id.background_translucent)

        // 플로팅 버튼 초기화 및 클릭 이벤트 처리
        btnFloating = view.findViewById(R.id.btn_floating)
        btnRectangle = view.findViewById(R.id.btn_rectangle)

        btnFloating.setOnClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                btnFloating.setImageResource(R.drawable.icon_x)
                btnRectangle.visibility = ImageButton.VISIBLE
                back.visibility = ImageView.VISIBLE
            } else {
                btnFloating.setImageResource(R.drawable.icon_plus)
                btnRectangle.visibility = ImageButton.GONE
                back.visibility = ImageView.GONE
            }
        }

        btnRectangle.setOnClickListener {
            val intent = Intent(activity, RegistrationTicketActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}

