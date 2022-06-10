package com.bootcamp.api_service.usecase

import com.bootcamp.api_service.service.MovieDetailService
import com.bootcamp.common.entity.base_response.AppResponse
import com.bootcamp.common.entity.movie_detail.MovieDetailResponse
import kotlinx.coroutines.flow.flow

class GetMovieDetailUseCase(private val movieDetailService: MovieDetailService) {
    operator fun invoke(movieId: Int) = flow {
        emit(AppResponse.loading())
        try {
            val response = movieDetailService.getMovieDetail(movieId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(AppResponse.success(it))
                } ?: run {
                    emit(
                        AppResponse.errorBackend<MovieDetailResponse>(
                            response.code(),
                            response.errorBody()
                        )
                    )
                }
            } else {
                emit(
                    AppResponse.errorBackend<MovieDetailResponse>(
                        response.code(),
                        response.errorBody()
                    )
                )
            }
        }catch (e:Exception){
            emit(AppResponse.errorSystem(e))
        }


    }
}