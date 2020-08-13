package com.ryspay.onelabfinalproject.feature.unsplash.data.paging

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.ryspay.onelabfinalproject.api.UnsplashPhotoApi
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.dao.PhotosDao
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.toDomainModel
import com.ryspay.onelabfinalproject.feature.unsplash.data.toLocalEntity
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.toPresentationModel
import com.ryspay.onelabfinalproject.utils.Const.Companion.DEFAULT_PER_PAGE
import com.ryspay.onelabfinalproject.utils.Const.Companion.ORDER_BY_LATEST
import com.ryspay.onelabfinalproject.utils.checkNetworkConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class PhotosPageKeyedDataSource(
    private val context: Context,
    private val localDataSource: PhotosLocalDataSource,
    private val remoteDataSource: PhotosRemoteDataSource,
    private val orderBy: String
): PageKeyedDataSource<Int, PhotoItemUI?>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItemUI?>
    ) {
        Log.d("DataSource.kt", "order_by: $orderBy")
        networkState.postValue(NetworkState.Loading)
        GlobalScope.launch(Dispatchers.IO) {
            if (checkNetworkConnection(context)) {
                try {
                    val photosResponse = remoteDataSource.getPhotos(1, DEFAULT_PER_PAGE, orderBy)
                    localDataSource.deleteAllPhotos()
                    localDataSource.savePhotos(photosResponse.map { it.toLocalEntity() })

                    networkState.postValue(NetworkState.Success)
                    callback.onResult(
                        localDataSource.getPhotos().map { it.toDomainModel().toPresentationModel() },
                        null,
                        2
                    )
                } catch (e: Exception) {
                    networkState.postValue(
                        NetworkState.Error(
                            e.message ?: "Something went wrong..."
                        )
                    )
                }
            } else {
                networkState.postValue(NetworkState.Error("Check network connection"))
                callback.onResult(
                    localDataSource.getPhotos().map { it.toDomainModel().toPresentationModel() },
                    null,
                    null
                )
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PhotoItemUI?>
    ) {
        Log.d("DataSource.kt", "order_by: $orderBy")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val photosResponse =
                    remoteDataSource.getPhotos(params.key, DEFAULT_PER_PAGE, orderBy)
                localDataSource.deleteAllPhotos()
                localDataSource.savePhotos(photosResponse.map { it.toLocalEntity() })

                networkState.postValue(NetworkState.Success)
                callback.onResult(
                    localDataSource.getPhotos().map { it.toDomainModel().toPresentationModel() },
                    params.key + 1
                )
            }catch (e: Exception) {
                networkState.postValue(NetworkState.Error(e.message?:"Something went wrong..."))
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PhotoItemUI?>
    ) {
    }

    class Factory(
        private val context: Context,
        private val localDataSource: PhotosLocalDataSource,
        private val remoteDataSource: PhotosRemoteDataSource
    ): DataSource.Factory<Int, PhotoItemUI?>() {

        var order_by: String = ORDER_BY_LATEST

        val mutableDataSource = MutableLiveData<PhotosPageKeyedDataSource>()

        override fun create(): DataSource<Int, PhotoItemUI?> {
            val dataSource = PhotosPageKeyedDataSource(
                context = context,
                localDataSource = localDataSource,
                remoteDataSource = remoteDataSource,
                orderBy = order_by
            )
            mutableDataSource.postValue(dataSource)
            return dataSource
        }
    }

}