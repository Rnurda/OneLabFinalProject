package com.ryspay.onelabfinalproject.feature.unsplash.presentation.search

import androidx.lifecycle.*
import com.ryspay.onelabfinalproject.feature.unsplash.data.SearchListing
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.GetSearchPagedPhotosUseCase
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI

class SearchViewModel (
    getSearchPagedPhotosUseCase: GetSearchPagedPhotosUseCase
): ViewModel(){
    private val _navigationState = MutableLiveData<NavigationState>()
    val navigationState: LiveData<NavigationState> get() = _navigationState

    fun onDetailActivityNavigationEvent(id: String){
        _navigationState.value = NavigationState.ToDetailsActivity(id)
    }

    private val pagedSearchPhotosList = MutableLiveData<SearchListing<PhotoItemUI?>>()
    val pagedSearchPhotos = Transformations.switchMap(pagedSearchPhotosList) { it.pagedList }
    val pagingState = Transformations.switchMap(pagedSearchPhotosList) { it.networkState }

    init {
        pagedSearchPhotosList.value = getSearchPagedPhotosUseCase()
    }

    fun onRefresh(query: String, orderBy: String) {
        pagedSearchPhotosList.value?.refresh?.invoke(
            orderBy, query
        )
    }
}

sealed class NavigationState{
    class ToDetailsActivity(val id: String): NavigationState()
}