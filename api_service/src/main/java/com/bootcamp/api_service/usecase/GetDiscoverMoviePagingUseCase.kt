package com.bootcamp.api_service.usecase

import com.bootcamp.api_service.paging.DiscoverMoviesPager
import com.bootcamp.api_service.service.DiscoverMovieService

class GetDiscoverMoviePagingUseCase (
    private val discoverMovieService: DiscoverMovieService
) {
    operator fun invoke(args: String?) =
        DiscoverMoviesPager.createPager(10, discoverMovieService, args).flow
}