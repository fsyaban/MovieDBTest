package com.bootcamp.api_service.usecase

import com.bootcamp.api_service.paging.MovieReviewPager
import com.bootcamp.api_service.service.MovieDetailService

class GetMovieReviewPagingUseCase (
    private val movieReviewService: MovieDetailService
) {
    operator fun invoke(movieId:Int) =
        MovieReviewPager.createPager(10, movieReviewService, movieId).flow
}