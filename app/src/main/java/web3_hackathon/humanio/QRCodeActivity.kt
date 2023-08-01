package web3_hackathon.humanio

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import dagger.hilt.android.AndroidEntryPoint
import web3_hackathon.humanio.databinding.ActivityQrcodeBinding

@AndroidEntryPoint
class QRCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text = "https://www.naver.com"
        generateBitmapQRCode(text)

        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix = multiFormatWriter.encode("https://www.naver.com", BarcodeFormat.QR_CODE, 200, 200)
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.createBitmap(bitMatrix)
        binding.ivQrCode.setImageBitmap(bitmap)

        binding.ivArrowBack.setOnClickListener {
            finish()
        }
    }

    private fun generateBitmapQRCode(contents: String): Bitmap {
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.encodeBitmap(contents, BarcodeFormat.QR_CODE, 512, 512)
    }

    companion object {
        fun getIntent(context: Context) =
            Intent(context, QRCodeActivity::class.java)
    }
}