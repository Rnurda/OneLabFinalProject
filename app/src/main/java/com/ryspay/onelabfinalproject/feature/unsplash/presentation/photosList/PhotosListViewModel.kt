package com.ryspay.onelabfinalproject.feature.unsplash.presentation.photosList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ryspay.onelabfinalproject.feature.unsplash.data.Listing
import com.ryspay.onelabfinalproject.feature.unsplash.data.paging.NetworkState
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.GetPagedPhotosUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.utils.Const.Companion.ORDER_BY_LATEST

class PhotosListViewModel(
    getPagedPhotosUseCase: GetPagedPhotosUseCase
): ViewModel() {

    private val _navigationState = MutableLiveData<NavigationState>()
    val navigationState: LiveData<NavigationState> get() = _navigationState

    private val pagedPhotosList = MutableLiveData<Listing<PhotoItemUI?>>()
    val pagedPhotos = switchMap(pagedPhotosList){ it.pagedList }
    val pagingState = switchMap(pagedPhotosList){ it.networkState }

    init {
        pagedPhotosList.value = getPagedPhotosUseCase()
    }

    fun onRefresh(orderBy: String) {
        pagedPhotosList.value?.refresh?.invoke(orderBy)
    }

    fun onDetailActivityNavigationEvent(id: String){
        _navigationState.value = NavigationState.ToDetailsActivity(id)
    }

    fun onSearchActivityNavigationEvent(){
        _navigationState.value = NavigationState.ToSearchActivity
    }
}

sealed class NavigationState{
    class ToDetailsActivity(val id: String): NavigationState()
    object ToSearchActivity: NavigationState()
}