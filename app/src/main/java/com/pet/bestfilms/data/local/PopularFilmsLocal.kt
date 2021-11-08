package com.pet.bestfilms.data.local


import com.google.gson.annotations.SerializedName

data class PopularFilmsLocal(
    @SerializedName("page")
    var page: Int?,
    @SerializedName("results")
    var results: List<Result> = arrayListOf(),
    @SerializedName("total_pages")
    var totalPages: Int?,
    @SerializedName("total_results")
    var totalResults: Int?
) {
    data class Result(
        @SerializedName("backdrop_path")
        var backdropPath: String?,
        @SerializedName("genre_ids")
        var genreIds: List<Int> = arrayListOf(),
        @SerializedName("id")
        var id: Int?,
        @SerializedName("original_language")
        var originalLanguage: String?,
        @SerializedName("original_title")
        var originalTitle: String?,
        @SerializedName("overview")
        var overview: String?,
        @SerializedName("popularity")
        var popularity: Double?,
        @SerializedName("poster_path")
        var posterPath: String?,
        @SerializedName("release_date")
        var releaseDate: String?,
        @SerializedName("title")
        var title: String?,
        @SerializedName("vote_average")
        var voteAverage: Double?,
        @SerializedName("vote_count")
        var voteCount: Int?
    )
}