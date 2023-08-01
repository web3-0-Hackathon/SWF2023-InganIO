package web3_hackathon.humanio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private lateinit var btnFloating: FloatingActionButton
    private lateinit var btnRectangle: ImageButton
    private var isButtonClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //val hometoolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)

        // 플로팅 버튼 초기화 및 클릭 이벤트 처리
        btnFloating = view.findViewById(R.id.btn_floating)
        btnRectangle = view.findViewById(R.id.btn_rectangle)

        btnFloating.setOnClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                btnFloating.setImageResource(R.drawable.icon_x)
                btnRectangle.visibility = ImageButton.VISIBLE
            } else {
                btnFloating.setImageResource(R.drawable.icon_plus)
                btnRectangle.visibility = ImageButton.GONE
            }
        }

        // 상단바 메뉴 초기화
        //hometoolbar.menu.clear()
        //hometoolbar.inflateMenu(R.menu.home_menu)

        return view
    }
}

