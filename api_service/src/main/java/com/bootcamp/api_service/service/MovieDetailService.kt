package com.bootcamp.api_service.service

import com.bootcamp.api_service.Const
import com.bootcamp.common.entity.movie_detail.MovieDetailResponse
import com.bootcamp.common.entity.movie_review.MovieReviewResponse
import com.bootcamp.common.entity.movie_video.MovieVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailService {
    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId:Int,
        @Query("api_key") api:String = Const.API_KEY
    ): Response<MovieDetailResponse>

    @GET("movie/{id}/reviews")
    suspend fun getReviews(
        @Path("id")movieId:Int,
        @Query("api_key") api:String = Const.API_KEY,
        @Query("page")page:Int = 1
    ) : Response<MovieReviewResponse>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") id:Int,
        @Query("api_key")api_key:String = Const.API_KEY,
    ): Response<MovieVideoResponse>

}