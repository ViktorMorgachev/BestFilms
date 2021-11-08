package com.pet.bestfilms.view.films.viewmodel

import androidx.lifecycle.viewModelScope
import com.pet.bestfilms.data.doOnError
import com.pet.bestfilms.data.doOnSuccess
import com.pet.bestfilms.data.popular_films.FilmsRepository
import com.pet.bestfilms.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository,
) : BaseViewModel() {

    val popularFilmsResult = MutableStateFlow<PopularFilmsResult>(PopularFilmsResult.EmptyResult)

    init {
        viewModelScope.launch {
            popularFilmsResult.emit(PopularFilmsResult.Loading)
            filmsRepository.loadPopularFilms()
                .doOnError {
                    popularFilmsResult.emit(PopularFilmsResult.ErrorResult(exception = it as Exception))
                }.doOnSuccess {
                    popularFilmsResult.emit(PopularFilmsResult.SuccessResult(data = it))
                }
        }
    }
}