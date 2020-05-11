package com.tst.networkprint

import com.tst.networkprint.util.GeneralUtil

class PrintData(
    val data: String,
    val isText: Boolean = true,
    val align: ByteArray = GeneralUtil.getNetworkPrintAlignment(0),
    val textSize: ByteArray = GeneralUtil.getNetworkPrintFont(0))