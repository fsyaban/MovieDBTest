package com.bootcamp.api_service.service

import com.bootcamp.api_service.Const
import com.bootcamp.common.entity.discover_movie.DiscoverMovieResponse
import com.bootcamp.common.entity.genre.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMovieService {
    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("api_key")apiKey:String = Const.API_KEY,
        @Query("with_genres") genres :String?,
        @Query("page")page:Int=1
    ):Response<DiscoverMovieResponse>
}