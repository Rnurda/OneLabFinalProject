package com.ryspay.onelabfinalproject.feature.unsplash.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.GetDetailedInfoPhotoUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.toPresentationModel
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.entity.DetailPhotoItemUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(
    private val photo_id: String,
    private val getDetailedInfoPhotoUseCase: GetDetailedInfoPhotoUseCase
): ViewModel() {

    private val _detailPhoto = MutableLiveData<DetailState<DetailPhotoItemUI>>().also {
        viewModelScope.launch(Dispatchers.IO) {
            it.postValue(DetailState.Loading)
            try {
                Log.d("DetailViewModel", "$photo_id")
                val details = getDetailedInfoPhotoUseCase(photo_id)
                Log.d("DetailViewModel", "$details")
                it.postValue(DetailState.Success(details.toPresentationModel()))
            }catch (e: Exception){
                it.postValue(DetailState.Error(e.message?:"Oops..."))
            }
        }
    }
    val detailPhoto: LiveData<DetailState<DetailPhotoItemUI>> get() = _detailPhoto

}

sealed class DetailState<out T> {
    object Loading: DetailState<Nothing>()
    class Success<T>(val data: T): DetailState<T>()
    class Error<T>(val message: String): DetailState<T>()
}