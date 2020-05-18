package com.tst.barcode_qr

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.tst.barcode_qr.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barcode = generate("866985389668", BarcodeFormat.CODE_128, 400, 100)
        binding.barCode.setImageBitmap(barcode)

        val qr = generate("https://github.com/AmaldevTA", BarcodeFormat.QR_CODE, 200, 200)
        binding.qr.setImageBitmap(qr)
    }

    private fun generate(source: String, format: BarcodeFormat, width: Int, height: Int): Bitmap {
        val result: BitMatrix = MultiFormatWriter().encode(source, format, width, height)
        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h)
        return bitmap
    }
}
