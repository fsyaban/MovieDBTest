package com.bootcamp.api_service.usecase

import com.bootcamp.api_service.service.GenreService
import com.bootcamp.common.entity.base_response.AppResponse
import com.bootcamp.common.entity.genre.Genre
import com.bootcamp.common.entity.genre.GenreResponse
import kotlinx.coroutines.flow.flow

class GetGenreUseCase(private val genreService: GenreService) {
    operator fun invoke() = flow {
        emit(AppResponse.loading())
        try {
            val response = genreService.getGenre()
            if (response.isSuccessful){
                response.body()?.let {
                    emit(AppResponse.success(it.genres))
                } ?: run{
                    emit(AppResponse.errorBackend<List<Genre>>(response.code(),
                        response.errorBody()))
                }
            } else {
                emit(AppResponse.errorBackend(response.code(),
                    response.errorBody()))
            }
        } catch (e:Exception){
            emit(AppResponse.errorSystem(e))
        }
    }
}