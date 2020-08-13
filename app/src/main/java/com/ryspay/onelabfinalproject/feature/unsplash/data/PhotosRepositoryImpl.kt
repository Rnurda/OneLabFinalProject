package com.ryspay.onelabfinalproject.feature.unsplash.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.paging.NetworkState
import com.ryspay.onelabfinalproject.feature.unsplash.data.paging.PhotosPageKeyedDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.utils.Const
import com.ryspay.onelabfinalproject.utils.checkNetworkConnection

class PhotosRepositoryImpl(
    private val context: Context,
    private val remoteDataSource: PhotosRemoteDataSource,
    private val localDataSource: PhotosLocalDataSource
): PhotosRepository {

    override suspend fun getPhotos(): List<PhotoDomain> =
        localDataSource.getPhotos().map { it.toDomainModel() }

    override suspend fun fetchPhotos(page: Int, per_page: Int, order_by: String) {
        if (checkNetworkConnection(context)){
            val photoResponse = remoteDataSource.getPhotos(
                page = page,
                per_page = per_page,
                order_by = order_by
            )

            localDataSource.deleteAllPhotos()
            localDataSource.savePhotos(photoResponse.map { it.toLocalEntity() })
        }
    }

    override suspend fun getPhoto(photo_id: String): DetailPhotoDomain {
        return remoteDataSource.getPhoto(photo_id).toDomainModel()
    }

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        per_page: Int,
        order_by: String,
        color: String?
    ): List<PhotoDomain> {
        val hashMap = HashMap<String, String>()

        color?.let{
            hashMap["color"] = color
        }

        val searchResponse = remoteDataSource.searchPhotos(
            query = query,
            per_page = per_page,
            page = page,
            order_by = order_by,
            map = hashMap
        )

        return searchResponse.results.map {
            it.toDomainModel()
        }
    }

    override fun getPhotosFactory(
        order_by: String
    ): PhotosPageKeyedDataSource.Factory {
        return PhotosPageKeyedDataSource.Factory(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            context = context
        )
    }

    override fun getPagedPhotos(order_by: String) : Listing<PhotoItemUI?>{
        val factory = PhotosPageKeyedDataSource.Factory(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            context = context
        )

        val pageConfig =
            PagedList.Config.Builder()
                .setPageSize(Const.DEFAULT_PER_PAGE)
                .setEnablePlaceholders(true)
                .build()

        val pagedList = LivePagedListBuilder(factory, pageConfig)
                .build()

        return Listing(
            pagedList = pagedList,
            networkState = Transformations.switchMap(factory.mutableDataSource) {
                it.networkState
            },
            refresh = {
                factory.order_by = it
                factory.mutableDataSource.value?.invalidate()
            }
        )
    }

}

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val refresh: (orderBy: String) -> Unit
)