package com.bootcamp.moviedbtest.module

import com.bootcamp.api_service.service.DiscoverMovieService
import com.bootcamp.api_service.service.GenreService
import com.bootcamp.api_service.service.MovieDetailService
import com.bootcamp.api_service.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun providesGenreUseCase(genreService: GenreService) =
        GetGenreUseCase(genreService)

    @Provides
    fun providesDiscoverUseCase(discoverMovieService: DiscoverMovieService)=
        GetDiscoverMoviePagingUseCase(discoverMovieService)

    @Provides
    fun providesMovieDetailUseCase(movieDetailService: MovieDetailService)=
        GetMovieDetailUseCase(movieDetailService)

    @Provides
    fun providesMovieReviewUseCase(movieReviewService: MovieDetailService)=
        GetMovieReviewPagingUseCase(movieReviewService)

    @Provides
    fun providesMovieVideoUseCase(movieVideoService: MovieDetailService)=
        GetMovieVideoUseCase(movieVideoService)

    @Provides
    fun providesDetailUseCase(movieVideoService: MovieDetailService)=
        MovieDetailUseCase(movieVideoService)

}