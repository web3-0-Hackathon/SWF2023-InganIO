package web3_hackathon.humanio

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class RegistrationTicketActivity : AppCompatActivity() {

    private lateinit var selectedEventDateStart: EditText
    private lateinit var selectedEventDateFinish: EditText
    private lateinit var selectedSaleDateStart: EditText
    private lateinit var selectedSaleDateFinish: EditText
    private lateinit var btnPriceProposal: Button
    private lateinit var imgThumbnail: ImageView
    private val calendar: Calendar = Calendar.getInstance()
    private val REQUEST_IMAGE_PICK = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_ticket)

        selectedEventDateStart = findViewById(R.id.selectedEventDateStart)
        selectedEventDateFinish = findViewById(R.id.selectedEventDateFinish)
        selectedSaleDateStart = findViewById(R.id.selectedSaleDateStart)
        selectedSaleDateFinish = findViewById(R.id.selectedSaleDateFinish)

        btnPriceProposal = findViewById(R.id.btn_price_proposal)

        imgThumbnail = findViewById(R.id.img_thumbnail)

        // selectedEventDateStart 클릭 시 날짜 선택 다이얼로그 표시
        selectedEventDateStart.setOnClickListener {
            showStartDatePickerDialog(it)
        }

        // selectedEventDateFinish 클릭 시 날짜 선택 다이얼로그 표시
        selectedEventDateFinish.setOnClickListener {
            showFinishDatePickerDialog(it)
        }

        //버튼 체크시 디자인 변경
        btnPriceProposal.setOnClickListener {
            btnChecked()
        }

        // 사진 선택 버튼을 클릭했을 때
        findViewById<ImageView>(R.id.img_thumbnail).setOnClickListener {
            openImagePicker()
        }
    }

    // 시작 날짜 선택 다이얼로그를 보여주는 메서드
    fun showStartDatePickerDialog(view: View) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(year1, monthOfYear, dayOfMonth)
                showStartTimePickerDialog()
            }, year, month, day)

        datePickerDialog.show()
    }

    // 시작 시간 선택 다이얼로그를 보여주는 메서드
    private fun showStartTimePickerDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute1: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute1)
                updateStartDateTimeText()
            }, hour, minute, false)

        timePickerDialog.show()
    }

    // 선택한 시작 날짜와 시간을 EditText에 표시하는 메서드
    private fun updateStartDateTimeText() {
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 (E)\na h:mm", Locale.getDefault())
        val dateTime = sdf.format(calendar.time)
        selectedEventDateStart.setText(dateTime)
    }

    // 종료 날짜 선택 다이얼로그를 보여주는 메서드
    fun showFinishDatePickerDialog(view: View) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
                calendar.set(year1, monthOfYear, dayOfMonth)
                showFinishTimePickerDialog()
            }, year, month, day)

        datePickerDialog.show()
    }

    // 종료 시간 선택 다이얼로그를 보여주는 메서드
    private fun showFinishTimePickerDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute1: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute1)
                updateFinishDateTimeText()
            }, hour, minute, false)

        timePickerDialog.show()
    }

    // 선택한 종료 날짜와 시간을 EditText에 표시하는 메서드
    private fun updateFinishDateTimeText() {
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 (E)\na h:mm", Locale.getDefault())
        val dateTime = sdf.format(calendar.time)
        selectedEventDateFinish.setText(dateTime)
    }

    //버튼 체크 시 디자인 변경하는 메서드
    private fun btnChecked() {
        var isChecked = false

        isChecked = !isChecked
        if (isChecked) {
            btnPriceProposal.setBackgroundResource(R.drawable.icon_checked)
        } else {
            btnPriceProposal.setBackgroundResource(R.drawable.icon_unchecked)
        }
    }
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            imgThumbnail.setImageURI(imageUri)
        }
    }

}