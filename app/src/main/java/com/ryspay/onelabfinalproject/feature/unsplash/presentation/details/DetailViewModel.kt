package com.ryspay.onelabfinalproject.feature.unsplash.presentation.details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.GetDetailedInfoPhotoUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.toPresentationModel
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.entity.DetailPhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.search.NavigationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DetailViewModel(
    private val photo_id: String,
    private val getDetailedInfoPhotoUseCase: GetDetailedInfoPhotoUseCase
): ViewModel() {

    private val _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    private val _detailPhoto = MutableLiveData<PhotoDetailState<DetailPhotoItemUI>>().also {
        viewModelScope.launch(Dispatchers.IO) {
            it.postValue(PhotoDetailState.Loading)
            try {
                Log.d("DetailViewModel", "$photo_id")
                val details = getDetailedInfoPhotoUseCase(photo_id)
                Log.d("DetailViewModel", "$details")
                it.postValue(PhotoDetailState.Success(details.toPresentationModel()))
            }catch (e: Exception){
                it.postValue(PhotoDetailState.Error(e.message?:"Oops..."))
            }
        }
    }
    val photoDetailPhoto: LiveData<PhotoDetailState<DetailPhotoItemUI>> get() = _detailPhoto
    
    fun onDownloadClicked(urlString: String){
        _detailState.value = DetailState.DownloadImageState(urlString)
    }
}

sealed class DetailState{
    class DownloadImageState(val url: String): DetailState()
}

sealed class PhotoDetailState<out T> {
    object Loading: PhotoDetailState<Nothing>()
    class Success<T>(val data: T): PhotoDetailState<T>()
    class Error<T>(val message: String): PhotoDetailState<T>()
}