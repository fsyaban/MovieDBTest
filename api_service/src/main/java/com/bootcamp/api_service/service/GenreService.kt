package com.bootcamp.api_service.service

import com.bootcamp.api_service.Const
import com.bootcamp.common.entity.genre.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {

    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api:String = Const.API_KEY
    ):Response<GenreResponse>
}