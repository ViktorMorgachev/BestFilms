package com.pet.bestfilms.data.popular_films

import com.pet.bestfilms.FilmsUrlProvider
import com.pet.bestfilms.data.remote.PopularFilmsResponse
import com.pet.bestfilms.data.network.QueryInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

interface FilmsApi {
    @GET("movie/popular")
    suspend fun fetchPopularFilms(): PopularFilmsResponse
}

interface FilmsRemoteProvider{
    val filmsApi: FilmsApi
}

class FilmsRemoteProviderImpl @Inject constructor(filmsUrlProvider: FilmsUrlProvider): FilmsRemoteProvider{

    override val filmsApi: FilmsApi by lazy(LazyThreadSafetyMode.NONE) { retrofit.create(FilmsApi::class.java) }

    private val client = OkHttpClient.Builder()
        .addInterceptor(QueryInterceptor(hashMapOf("api_key" to filmsUrlProvider.apiKey)))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(filmsUrlProvider.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}