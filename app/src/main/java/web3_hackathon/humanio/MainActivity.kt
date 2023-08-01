package web3_hackathon.humanio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var bnv_main = findViewById(R.id.bnv_main) as BottomNavigationView

        bnv_main.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.first -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val homeFragment = HomeBeforeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, homeFragment).commit()
                }
                R.id.second -> {
                    val menuFragment = MenuFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, menuFragment).commit()
                }
                R.id.third -> {
                    val searchFragment = SearchFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, searchFragment).commit()
                }
                R.id.fourth -> {
                    val myFragment = MyPageFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, myFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.first
        }
    }
}

