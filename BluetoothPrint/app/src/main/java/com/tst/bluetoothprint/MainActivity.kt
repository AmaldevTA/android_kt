package com.tst.bluetoothprint

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tst.bluetoothprint.databinding.ActivityMainBinding
import com.tst.bluetoothprint.util.Commands
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val printers: MutableList<BluetoothDevice> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.isEnabled.text = isBluetoothEnabled().toString()

        binding.refresh.setOnClickListener {
            printers.clear()
            printers.addAll(connectedBluetoothPrinter())
            binding.connected.text = printers.size.toString()
        }

        binding.print.setOnClickListener {
            if(printers.isNotEmpty()){
                val data: MutableList<PrintData> = ArrayList()
                data.add(PrintData("HeadLine", align = Commands.TXT_ALIGN_CT, underline = Commands.TXT_UNDERL_ON))
                data.add(PrintData("Line1", align = Commands.TXT_ALIGN_LT, underline = Commands.TXT_UNDERL_OFF))
                data.add(PrintData("Line2"))
                data.add(PrintData("Line3"))
                data.add(PrintData("Line4"))

                Thread(Runnable {
                    print(printers[0], data)
                }).start()
            }
        }
    }

    private fun isBluetoothEnabled() : Boolean{
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return mBluetoothAdapter?.isEnabled ?: false
    }

    private fun requestEnableBluetooth(){
        val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBluetooth, 150)
    }

    private fun connectedBluetoothPrinter(): List<BluetoothDevice> {
        val printers: MutableList<BluetoothDevice> = ArrayList()
        try {
            val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val pairedDevices = mBluetoothAdapter.bondedDevices
            if (pairedDevices.size > 0) {
                for (device in pairedDevices) {
                    if (isAPrinter(device)) {
                        printers.add(device)
                    }
                }
            }
        } catch (ignore: Exception) { }
        return printers
    }

    private fun isAPrinter(device: BluetoothDevice): Boolean {
        val printerMask = 0b000001000000011010000000
        val fullCod = device.bluetoothClass.hashCode()
        return (fullCod and printerMask) == printerMask
    }


    fun print(
        device: BluetoothDevice,
        data: List<PrintData>
    ) {
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
        val mmSocket = device.createRfcommSocketToServiceRecord(uuid)
        mmSocket.connect()
        val mmOutputStream = mmSocket.outputStream
        for (printData in data) {
            if (printData.isText) {
                mmOutputStream.write(printData.align)
                mmOutputStream.write(printData.bold)
                mmOutputStream.write(printData.textSize)
                mmOutputStream.write(printData.underline)
                mmOutputStream.write(printData.data.toByteArray())
                mmOutputStream.write("\r\n".toByteArray())
            }
        }
        mmOutputStream.write("\n".toByteArray())
        mmOutputStream.close()
        mmSocket.close()
    }
}
