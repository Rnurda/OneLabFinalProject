package com.ryspay.onelabfinalproject.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun checkNetworkConnection(context: Context): Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    var result = false
    if (activeNetwork != null) {
        result = activeNetwork.isConnectedOrConnecting
    }
    return result
}