package com.tst.bluetoothprint

import com.tst.bluetoothprint.util.Commands

class PrintData(
    val data: String,
    val isText: Boolean = true,
    val align: ByteArray = Commands.LINE_LEFT,
    val textSize: ByteArray = Commands.TXT_NORMAL,
    val bold: ByteArray = Commands.TXT_BOLD_OFF,
    val underline: ByteArray = Commands.TXT_UNDERL_OFF)