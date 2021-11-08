package com.pet.bestfilms.view.films.viewmodel

import androidx.paging.PagingSource
import com.pet.bestfilms.data.Result
import com.pet.bestfilms.data.local.PopularFilmsLocal
import com.pet.bestfilms.data.popular_films.FilmsRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class MoviesAdapter @Inject constructor(val filmsRepository: FilmsRepository) :
    PagingSource<Int, PopularFilmsLocal>() {

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularFilmsLocal.Result> {
        val page = params.key ?: 0
        val response = GlobalScope.async { filmsRepository.loadPopularFilms() }.await()
        return when (response) {
            is Result.Success -> {
                LoadResult.Page(
                    response.result.results,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (response.result.results.isEmpty()) null else page + 1
                )
            }
            is Result.Error -> {
                LoadResult.Error(response.errorInfo)
            }
        }
    }

}