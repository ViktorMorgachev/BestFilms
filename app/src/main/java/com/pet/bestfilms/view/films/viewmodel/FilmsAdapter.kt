package com.pet.bestfilms.view.films.viewmodel

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.paging.PagingSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pet.bestfilms.data.Result
import com.pet.bestfilms.data.local.PopularFilmsLocal
import com.pet.bestfilms.data.popular_films.FilmsRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilmsPagingSource @Inject constructor(val filmsRepository: FilmsRepository) :
    PagingSource<Int, PopularFilmsLocal.Result>() {

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PopularFilmsLocal.Result> {
        val page = params.key ?: -1
        return when (val response =
            withContext(Dispatchers.Default) { filmsRepository.loadPopularFilms() }) {
            is Result.Success -> {
                LoadResult.Page(
                    response.result.results,
                    prevKey = if (page == -1) null else page - 1,
                    nextKey = if (response.result.results.isEmpty()) null else page + 1
                )
            }
            is Result.Error -> {
                LoadResult.Error(response.errorInfo)
            }
        }
    }
}

class FilmsAdapter : PagingDataAdapter<PopularFilmsLocal.Result, RecyclerView.ViewHolder>() {


    companion object{
        val comparator = object : DiffUtil.ItemCallback<PopularFilmsLocal.Result>() {
            override fun areItemsTheSame(
                oldItem: PopularFilmsLocal.Result,
                newItem: PopularFilmsLocal.Result,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PopularFilmsLocal.Result,
                newItem: PopularFilmsLocal.Result,
            ): Boolean {
              return oldItem == newItem
            }

        }
    }
    class FilmsViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }
}

enum class ItemType{
   Header, Footer, Item
}