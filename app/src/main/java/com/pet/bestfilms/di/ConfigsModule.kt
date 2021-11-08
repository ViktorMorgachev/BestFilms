package com.pet.bestfilms.di

import com.pet.bestfilms.BuildConfig.API_KEY
import com.pet.bestfilms.BuildConfig.BASE_URL
import com.pet.bestfilms.FilmsUrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConfigsModule {

    @Provides
    fun provideFilmsUrlProvider(): FilmsUrlProvider =
        FilmsUrlProvider(
            baseUrl = BASE_URL,
            apiKey = API_KEY,
            baseImageUrl = "https://image.tmdb.org/t/p/w300",
            browseMovieBaseUrl = "https://www.themoviedb.org/movie/"
        )
}