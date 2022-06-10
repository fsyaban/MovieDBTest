package com.bootcamp.api_service.usecase

import com.bootcamp.api_service.paging.MovieReviewPager
import com.bootcamp.api_service.service.MovieDetailService
import com.bootcamp.common.entity.base_response.AppResponse
import com.bootcamp.common.entity.movie_video.MovieVideoResponse as Video
import com.bootcamp.common.entity.movie_detail.MovieDetailResponse as Detail
import com.bootcamp.common.entity.movie_review.MovieReviewResponse as Review

import kotlinx.coroutines.flow.flow

class MovieDetailUseCase(val movieDetailService: MovieDetailService) {
    operator fun invoke(id: Int) = flow {
        try {
            emit(AppResponse.loading())
            val detailResponse = movieDetailService.getMovieDetail(id)
            if (detailResponse.isSuccessful) {
                val videoResponse = movieDetailService.getMovieVideos(id)
                if (videoResponse.isSuccessful) {
                    detailResponse.body()?.let { detail ->
                        videoResponse.body()?.let { video ->
                            emit(AppResponse.success(Pair(detail, video)))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            emit(AppResponse.errorSystem(e))
        }
    }
}