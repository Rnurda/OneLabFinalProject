package com.ryspay.onelabfinalproject.di

import com.ryspay.onelabfinalproject.api.UnsplashPhotoApi
import com.ryspay.onelabfinalproject.feature.unsplash.data.paging.PhotosPageKeyedDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.PhotosRepositoryImpl
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.RoomPhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.RetrofitPhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases.*
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.DetailViewModel
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.photosList.PhotosListViewModel
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.search.SearchViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val ACCESS_KEY = "19IuGC8Hki8L8eaFtmTglAD2AD4y9bhe2pFZSy_7S88"
const val BASE_URL = "https://api.unsplash.com/"

class ApiInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Accept-Version", "v1")
            .addHeader("Authorization", "Client-ID $ACCESS_KEY")
            .build()
        return chain.proceed(request)
    }
}

val mainModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create<UnsplashPhotoApi>(UnsplashPhotoApi::class.java)
    }

    single<PhotosRemoteDataSource> {
        RetrofitPhotosRemoteDataSource(
            api = get()
        )
    }

    single<PhotosLocalDataSource> {
        RoomPhotosLocalDataSource(
            dao = get()
        )
    }

    single<PhotosRepository>{
        PhotosRepositoryImpl(
            context = androidContext(),
            localDataSource = get(),
            remoteDataSource = get()
        )
    }

    factory {
        GetDetailedInfoPhotoUseCase(
            repository = get()
        )
    }

    factory {
        GetPagedPhotosUseCase(
            repository = get()
        )
    }

    factory {
        GetSearchPagedPhotosUseCase(
            repository = get()
        )
    }

    viewModel {
        PhotosListViewModel(
            getPagedPhotosUseCase = get()
        )
    }

    viewModel {(photo_id: String) ->
        DetailViewModel(
            photo_id = photo_id,
            getDetailedInfoPhotoUseCase = get()
        )
    }

    viewModel {
        SearchViewModel(
            getSearchPagedPhotosUseCase = get()
        )
    }

}