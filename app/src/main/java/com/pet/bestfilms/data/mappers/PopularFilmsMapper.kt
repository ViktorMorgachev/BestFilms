package com.pet.bestfilms.data.mappers

import com.pet.bestfilms.data.local.PopularFilmsLocal
import com.pet.bestfilms.data.remote.PopularFilmsResponse

object PopularFilmsMapper {

    fun PopularFilmsResponse.toFilmData(): PopularFilmsLocal =
        PopularFilmsLocal(page = page, results = results.map { it.toPopularFilmLocalResult() }, totalPages, totalResults)

    private fun PopularFilmsResponse.Result.toPopularFilmLocalResult(): PopularFilmsLocal.Result {
        return PopularFilmsLocal.Result(backdropPath,
            genreIds,
            id,
            originalLanguage,
            originalTitle,
            overview,
            popularity,
            posterPath,
            releaseDate,
            title,
            voteAverage,
            voteCount)
    }
}


