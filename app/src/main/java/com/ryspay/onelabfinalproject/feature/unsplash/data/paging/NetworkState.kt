package com.ryspay.onelabfinalproject.feature.unsplash.data.paging

sealed class NetworkState{
    object Loading: NetworkState()
    class Error(val msg: String): NetworkState()
    object Success: NetworkState()
}
