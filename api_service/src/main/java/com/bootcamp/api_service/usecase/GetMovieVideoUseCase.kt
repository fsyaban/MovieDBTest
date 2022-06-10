package com.bootcamp.api_service.usecase

import com.bootcamp.api_service.service.MovieDetailService
import com.bootcamp.common.entity.base_response.AppResponse
import com.bootcamp.common.entity.movie_video.MovieVideoResponse
import kotlinx.coroutines.flow.flow

class GetMovieVideoUseCase(val movieVideoService: MovieDetailService) {
    operator fun invoke(movieId: Int) = flow{
        try {
            emit(AppResponse.loading())
            val response = movieVideoService.getMovieVideos(movieId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(AppResponse.success(it))
                } ?: run {
                    emit(
                        AppResponse.errorBackend<MovieVideoResponse>(
                            response.code(),
                            response.errorBody()
                        )
                    )
                }
            } else {
                emit(
                    AppResponse.errorBackend(
                        response.code(),
                        response.errorBody()
                    )
                )
            }
        } catch (e: Exception) {
            emit(AppResponse.errorSystem(e))
        }


    }
}