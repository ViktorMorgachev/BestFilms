package com.pet.bestfilms.view.films.viewmodel

import com.pet.bestfilms.data.local.PopularFilmsLocal
import java.lang.Exception

sealed class PopularFilmsResult {
    object EmptyResult : PopularFilmsResult()
    object Loading : PopularFilmsResult()
    class SuccessResult(val data: PopularFilmsLocal): PopularFilmsResult()
    class ErrorResult(val exception: Exception): PopularFilmsResult()
}