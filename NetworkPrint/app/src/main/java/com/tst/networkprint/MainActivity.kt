package com.tst.networkprint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tst.networkprint.databinding.ActivityMainBinding
import com.tst.networkprint.util.GeneralUtil
import java.io.ByteArrayOutputStream
import java.net.InetSocketAddress
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private val printerTimeOut = 3000
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.print.setOnClickListener {
            Thread{
                val ip = binding.ipAddress.text.toString()
                val port = binding.ipAddress.text.toString().toInt()
                if (isPrinterAvailable(ip, port)){
                   val data: MutableList<PrintData> = mutableListOf()
                    data.add(PrintData("HeadLine"))
                    data.add(PrintData("line1"))
                    data.add(PrintData("line2"))
                    printData(ip, port, data)
                }
            }.start()
        }
    }

    private fun printData(ip: String, port: Int, data: List<PrintData>){
        val outputStream = ByteArrayOutputStream()
        for (line in data){
            if(line.isText){
                outputStream.write(GeneralUtil.getNetworkPrintFont(0))
                outputStream.write(GeneralUtil.getNetworkPrintAlignment(0))
                outputStream.write(line.data.toByteArray())
                outputStream.write("\n".toByteArray())
            }else{
                outputStream.write(GeneralUtil.getNetworkPrintFont(0))
                outputStream.write(GeneralUtil.getNetworkPrintAlignment(0))
                val bmp = GeneralUtil.getBitmapFromURL(line.data)
                val command = GeneralUtil.decodeBitmap(bmp)
                if (command != null) {
                    outputStream.write(command)
                    outputStream.write("\n".toByteArray())
                }
            }
        }

        outputStream.write("\n".toByteArray())
        outputStream.write(byteArrayOf(0x1D, 0x56, 66, 0x00))

        printWithNetworkPrint(ip, port, outputStream.toByteArray())
    }

    private fun isPrinterAvailable(ip: String, port: Int): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress(ip, port), printerTimeOut)
            socket.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun printWithNetworkPrint(ip: String, port: Int, buffer: ByteArray): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress(ip, port), printerTimeOut)
            val outputStream = socket.getOutputStream()
            outputStream.write(buffer)
            socket.close()
            true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }
}
