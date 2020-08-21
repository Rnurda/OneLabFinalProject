package com.ryspay.onelabfinalproject.feature.unsplash.data.paging

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.toDomainModel
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.toPresentationModel
import com.ryspay.onelabfinalproject.utils.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SearchPhotosPageKeyedDataSource(
    private val remoteDataSource: PhotosRemoteDataSource,
    private val orderBy: String,
    private val query: String?
): PageKeyedDataSource<Int, PhotoItemUI?>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItemUI?>
    ) {
        query?.let {
            networkState.postValue(NetworkState.Loading)
            GlobalScope.launch(Dispatchers.IO) {
                try {

                    val photosResponse = remoteDataSource.searchPhotos(
                        query = it,
                        page = 1,
                        per_page = Const.DEFAULT_PER_PAGE,
                        order_by = orderBy,
                        map = HashMap()
                    )
                    networkState.postValue(NetworkState.Success)
                    Log.d("DataSource.kt", "${photosResponse.total}")
                    callback.onResult(
                        photosResponse.results.map { it.toDomainModel().toPresentationModel() },
                        null,
                        if (photosResponse.total_pages <= 1) null else 2
                    )
                } catch (e: Exception) {
                    networkState.postValue(
                        NetworkState.Error(
                            e.message ?: "Something went wrong..."
                        )
                    )
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItemUI?>) {
        query?.let {
            networkState.postValue(NetworkState.Loading)
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val photosResponse = remoteDataSource.searchPhotos(
                        query = it,
                        page = params.key,
                        per_page = Const.DEFAULT_PER_PAGE,
                        order_by = orderBy,
                        map = HashMap()
                    )
                    networkState.postValue(NetworkState.Success)
                    callback.onResult(
                        photosResponse.results.map { it.toDomainModel().toPresentationModel() },
                        if (photosResponse.total_pages > params.key) params.key + 1 else null
                    )
                } catch (e: Exception) {
                    networkState.postValue(
                        NetworkState.Error(
                            e.message ?: "Something went wrong..."
                        )
                    )
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItemUI?>) {

    }

    class Factory(
        private val remoteDataSource: PhotosRemoteDataSource
    ): DataSource.Factory<Int, PhotoItemUI?>() {

        var order_by: String = Const.ORDER_BY_LATEST
        var query: String? = null

        val mutableDataSource = MutableLiveData<SearchPhotosPageKeyedDataSource>()

        override fun create(): DataSource<Int, PhotoItemUI?> {
            val dataSource = SearchPhotosPageKeyedDataSource(
                remoteDataSource = remoteDataSource,
                orderBy = order_by,
                query = query
            )
            mutableDataSource.postValue(dataSource)
            return dataSource
        }

    }
}