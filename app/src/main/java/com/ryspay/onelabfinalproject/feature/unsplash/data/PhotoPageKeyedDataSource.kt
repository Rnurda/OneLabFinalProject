package com.ryspay.onelabfinalproject.feature.unsplash.data

import android.content.Context
import androidx.paging.PageKeyedDataSource
import com.ryspay.onelabfinalproject.api.UnsplashPhotoApi
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.dao.PhotosDao
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal
import com.ryspay.onelabfinalproject.utils.Const.Companion.DEFAULT_PER_PAGE
import com.ryspay.onelabfinalproject.utils.Const.Companion.ORDER_BY_LATEST
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoPageKeyedDataSource(
    private val photosDao: PhotosDao,
    private val photoApi: UnsplashPhotoApi
): PageKeyedDataSource<Int, PhotoLocal?>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoLocal?>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val photosResponse = photoApi.getPhotos(1, params.requestedLoadSize, ORDER_BY_LATEST)
            photosDao.deleteAllPhotos()
            photosDao.savePhotos(photosResponse.map { it.toLocalEntity() })
            callback.onResult(
                photosDao.getPhotos(),
                null,
                2
            )
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PhotoLocal?>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val photosResponse = photoApi.getPhotos(params.key, DEFAULT_PER_PAGE, ORDER_BY_LATEST)
            photosDao.deleteAllPhotos()
            photosDao.savePhotos(photosResponse.map { it.toLocalEntity() })
            callback.onResult(
                photosDao.getPhotos(),
                params.key + 1
            )
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, PhotoLocal?>
    ) {
//        GlobalScope.launch(Dispatchers.IO) {
//            val photosResponse = photoApi.getPhotos(params.key)
//            photosDao.deleteAllPhotos()
//            photosDao.savePhotos(photosResponse.map { it.toLocalEntity() })
//            callback.onResult(
//                photosDao.getPhotos(),
//                params.key - 1
//            )
//        }
    }

}