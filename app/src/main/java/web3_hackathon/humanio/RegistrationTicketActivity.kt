package web3_hackathon.humanio

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import web3_hackathon.humanio.data.api.human_io.HumanIONftApi
import web3_hackathon.humanio.data.api.octet.NftApi
import web3_hackathon.humanio.data.model.DeployNftContractRequest
import web3_hackathon.humanio.data.model.DeployNftContractResponse
import web3_hackathon.humanio.databinding.ActivityRegistrationTicketBinding
import web3_hackathon.humanio.di.NetworkModule
import java.text.SimpleDateFormat
import java.util.*

class RegistrationTicketActivity : AppCompatActivity() {

    private lateinit var eventNm: EditText
    private lateinit var eventLocation: EditText
    private lateinit var eventDate: EditText
    private lateinit var tokenUri: EditText
    private lateinit var startPrice: EditText
    private lateinit var amount: EditText
    private lateinit var startDate: EditText
    private lateinit var endDate: EditText
    private lateinit var tokenId: EditText
    private lateinit var symbol: EditText


    private lateinit var btnPriceProposal: Button
    private lateinit var imgThumbnail: ImageView
    private lateinit var btnsubmit: ImageView
    private val calendar: Calendar = Calendar.getInstance()
    private val REQUEST_IMAGE_PICK = 1

    private val nftApi: HumanIONftApi? = NetworkModule
        .provideHumanIOApi(NetworkModule.provideOkHttpClient())
        .create(HumanIONftApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registration_ticket)

        eventNm = findViewById(R.id.eventNm)
        eventLocation = findViewById(R.id.eventLocation)
        eventDate = findViewById(R.id.selectedEventDate)
        tokenUri = findViewById(R.id.tokenUri)
        startPrice = findViewById(R.id.startPrice)
        amount = findViewById(R.id.amount)
        startDate = findViewById(R.id.selectedSaleDateStart)
        endDate = findViewById(R.id.selectedSaleDateFinish)
        tokenId = findViewById(R.id.tokenId)
        symbol = findViewById(R.id.symbol)

        btnPriceProposal = findViewById(R.id.btn_price_proposal)

        btnsubmit = findViewById(R.id.submitButton)

        // selectedEventDate 클릭 시 날짜 선택 다이얼로그 표시
        eventDate.setOnClickListener {
            showDatePickerDialog(it)
        }

        // selectedEventDateStart 클릭 시 날짜 선택 다이얼로그 표시
        startDate.setOnClickListener {
            showStartDatePickerDialog(it)
        }

        // selectedEventDateFinish 클릭 시 날짜 선택 다이얼로그 표시
        endDate.setOnClickListener {
            showFinishDatePickerDialog(it)
        }

        //버튼 체크시 디자인 변경
        btnPriceProposal.setOnClickListener {
            btnChecked()
        }

        //작성 완료 버튼 클릭 시 서버로 데이터 전송
        btnsubmit.setOnClickListener(View.OnClickListener { view ->
            sendDataToServer()

            Toast.makeText(applicationContext,
                "작성이 완료되었습니다",
                Toast.LENGTH_SHORT).show()
        })
        initListeners()
    }

    // 날짜 선택 다이얼로그를 보여주는 메서드
    fun showDatePickerDialog(view: View) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                _: DatePicker, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
            calendar.set(year1, monthOfYear, dayOfMonth)
            showTimePickerDialog()
        }, year, month, day)

        datePickerDialog.show()
    }

    // 시간 선택 다이얼로그를 보여주는 메서드
    private fun showTimePickerDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hourOfDay: Int, minute1: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute1)
                updateDateTimeText()
            }, hour, minute, false)

        timePickerDialog.show()
    }

    // 선택한 날짜와 시간을 EditText에 표시하는 메서드
    private fun updateDateTimeText() {
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 (E) a h:mm", Locale.getDefault())
        val dateTime = sdf.format(calendar.time)
        eventDate.setText(dateTime)
    }

    // 시작 날짜 선택 다이얼로그를 보여주는 메서드
    fun showStartDatePickerDialog(view: View) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                _: DatePicker, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
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
        val sdf = SimpleDateFormat("yyyy년 MM월 dd일 (E) a h:mm", Locale.getDefault())
        val dateTime = sdf.format(calendar.time)
        startDate.setText(dateTime)
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
        endDate.setText(dateTime)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            imgThumbnail.setImageURI(imageUri)
        }
    }

    var uuid: String? = ""
    // 입력 받은 값을 서버로 보내는 메서드
    private fun sendDataToServer() {
        val eventNm = eventNm.text.toString()
        val eventLocation = eventLocation.text.toString()
        val eventDate = eventDate.text.toString()
        val tokenUri = tokenUri.text.toString()
        val startPrice = startPrice.text.toString()
        val amount = amount.text.toString()
        val startDate = startDate.text.toString()
        val endDate = endDate.text.toString()
        val symbol = symbol.text.toString()
        val tokenId = tokenId.text.toString()


        val deployNftContractRequest = DeployNftContractRequest(
            eventNm, eventLocation, eventDate, tokenUri, startPrice, amount, startDate, endDate, symbol, tokenId)

        val call = nftApi?.deployNftContract(deployNftContractRequest)

        call?.enqueue(object : Callback<DeployNftContractResponse> {
            override fun onResponse(
                call: Call<DeployNftContractResponse>,
                response: Response<DeployNftContractResponse>
            ) {
                if (response.isSuccessful) {
//                   Log.i("contractAddress", ": ${response.body()?.statusCode}")
                    uuid = response.body()?.uuid
                    Log.i("sendDataToServer", "onResponse: $uuid")

                    if (uuid != null) {
                        onClickSubmitButton(uuid)
                    }
                }
            }

            override fun onFailure(call: Call<DeployNftContractResponse>, t: Throwable) {
                // Handle the case when the API call failed
                t.printStackTrace()
                Log.e("DeployNftContractRequest", "API call failed with exception: ${t.message}")
            }
        })
    }

    private fun initListeners() {
        supportFragmentManager.setFragmentResultListener(
            AskBuyTicketDialog.TAG,
            this@RegistrationTicketActivity
        ) { _: String, result: Bundle ->
            val action = result.getString(AskRegistrationDialog.RESULT_ACTION)
            if (action == AskRegistrationDialog.ACTION_REGISTER) {

            }
        }
    }

    private fun onClickSubmitButton(uuid: String?) {
        val askRegistrationDialog = AskRegistrationDialog.newInstance(uuid)
        askRegistrationDialog.show(supportFragmentManager, AskRegistrationDialog.TAG)
    }
}