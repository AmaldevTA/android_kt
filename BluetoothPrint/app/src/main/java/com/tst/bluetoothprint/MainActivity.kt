package com.tst.bluetoothprint

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun connectedBluetoothPrinter(): List<BluetoothDevice>? {
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
