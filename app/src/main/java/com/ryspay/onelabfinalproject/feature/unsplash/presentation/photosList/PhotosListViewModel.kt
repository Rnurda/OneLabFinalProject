package com.ryspay.onelabfinalproject.feature.unsplash.presentation.photosList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.GetPhotosUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.toPresentationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PhotosListViewModel(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    private val _photos = MutableLiveData<PhotosState<List<PhotoItemUI>>>().also {
        viewModelScope.launch(Dispatchers.IO) {
            it.postValue(PhotosState.Loading)
            try{
                val photos = getPhotosUseCase()
                it.postValue(PhotosState.Success(photos.map { it.toPresentationModel() }))
            }catch (e: Exception){
                it.postValue(PhotosState.Error(e.message?:"Oops..."))
            }
        }
    }
    val photos: LiveData<PhotosState<List<PhotoItemUI>>> get() = _photos

}

sealed class PhotosState<out T>{
    object Loading : PhotosState<Nothing>()
    class Error<T>(val message: String): PhotosState<T>()
    class Success<T>(val data: T): PhotosState<T>()
}