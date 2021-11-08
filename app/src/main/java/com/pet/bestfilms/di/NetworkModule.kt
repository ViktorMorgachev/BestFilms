package com.pet.bestfilms.di

import com.pet.bestfilms.data.popular_films.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    @Singleton
    fun bindFilmsHttpClient(
        impl: FilmsRemoteProviderImpl,
    ): FilmsRemoteProvider

    @Binds
    fun bindMoviesRepository(
        impl: FilmsRepositoryImpl,
    ): FilmsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ApiWrapperModule {

    @Provides
    @Singleton
    fun provideMoviesApi(client: FilmsRemoteProvider): FilmsApi = client.filmsApi
}