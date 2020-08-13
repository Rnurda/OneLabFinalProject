package com.ryspay.onelabfinalproject.feature.unsplash.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.SearchPhotoUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.toPresentationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchViewModel (
    private val searchPhotoUseCase: SearchPhotoUseCase
): ViewModel(){
    private var page: Int = 1
    var _search_et = MutableLiveData<String>()

    private val _photos = MutableLiveData<PhotosState<List<PhotoItemUI>>>()
    val photos: LiveData<PhotosState<List<PhotoItemUI>>> get() = _photos

    private val _navigationState = MutableLiveData<NavigationState>()
    val navigationState: LiveData<NavigationState> get() = _navigationState

    fun onNextPage(query: String, color: String?){
        page += 1
        searchAndPostPhotos(query, color)
    }

    fun onPreviousPage(query: String, color: String?){
        if(page - 1 > 0){
            page -= 1
            searchAndPostPhotos(query, color)
        }
    }

    fun onDetailActivityNavigationEvent(id: String){
        _navigationState.value = NavigationState.ToDetailsActivity(id)
    }

    fun searchAndPostPhotos(query: String, color: String?){
        viewModelScope.launch(Dispatchers.Main) {
            _photos.postValue(PhotosState.Loading)
            try{
                _photos.postValue(
                    PhotosState.Success(searchPhotoUseCase(query = query, page = page, per_page = 30, color = color)
                        .map { it.toPresentationModel() })
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
}