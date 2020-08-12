package com.ryspay.onelabfinalproject.feature.unsplash.presentation.photosList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.FetchPhotosUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.GetPhotosUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.toPresentationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PhotosListViewModel(
    private val fetchPhotosUseCase: FetchPhotosUseCase,
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    private var page: Int = 1

    private val _photos = MutableLiveData<PhotosState<List<PhotoItemUI>>>().also {
        fetchAndPostPhotos()
    }
    val photos: LiveData<PhotosState<List<PhotoItemUI>>> get() = _photos

    private val _navigationState = MutableLiveData<NavigationState>()
    val navigationState: LiveData<NavigationState> get() = _navigationState

    fun onNextPage(){
        page += 1
        fetchAndPostPhotos()
    }

    fun onPreviousPage(){
        if(page - 1 > 0){
            page -= 1
            fetchAndPostPhotos()
        }
    }

    fun onDetailActivityNavigationEvent(id: String){
        _navigationState.value = NavigationState.ToDetailsActivity(id)
    }

    fun onSearchActivityNavigationEvent(){
        _navigationState.value = NavigationState.ToSearchActivity
    }

    private fun fetchAndPostPhotos(){
        viewModelScope.launch(Dispatchers.IO) {
            _photos.postValue(PhotosState.Loading)
            try{
                fetchPhotosUseCase(
                    page = page,
                    per_page = 30
                )
                _photos.postValue(
                    PhotosState.Success(getPhotosUseCase().map { it.toPresentationModel() })
                )
            }catch (e: Exception) {
                _photos.postValue(PhotosState.Error(e.message?:"Oops..."))
            }
        }
    }
}

sealed class PhotosState<out T>{
    object Loading : PhotosState<Nothing>()
    class Error<T>(val message: String): PhotosState<T>()
    class Success<T>(val data: T): PhotosState<T>()
}

sealed class NavigationState{
    class ToDetailsActivity(val id: String): NavigationState()
    object ToSearchActivity: NavigationState()
}