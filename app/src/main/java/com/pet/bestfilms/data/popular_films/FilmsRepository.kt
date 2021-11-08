package com.pet.bestfilms.data.popular_films

import com.pet.bestfilms.data.Result
import com.pet.bestfilms.data.doOnError
import com.pet.bestfilms.data.local.PopularFilmsLocal
import com.pet.bestfilms.data.mapOnSuccess
import com.pet.bestfilms.data.mappers.PopularFilmsMapper.toFilmData
import com.pet.bestfilms.data.tryGetResult
import java.lang.Exception
import javax.inject.Inject

interface FilmsRepository {
    suspend fun loadPopularFilms(): Result<PopularFilmsLocal, Throwable>
}

class FilmsRepositoryImpl @Inject constructor(private val filmsRemoteProvider: FilmsRemoteProvider) : FilmsRepository {

    private var popularFilmsResult: PopularFilmsLocal? = null

    override suspend fun loadPopularFilms(): Result<PopularFilmsLocal, Throwable> {
        return if (popularFilmsResult == null) {
            tryGetResult {
                filmsRemoteProvider.filmsApi.fetchPopularFilms()
            }.doOnError {
                Result.Error(Exception("loadPopularFilms is fails"))
            }.mapOnSuccess{
                popularFilmsResult =  it.toFilmData()
                popularFilmsResult!!
            }
        } else {
            Result.Success(popularFilmsResult!!)
        }
    }
}


