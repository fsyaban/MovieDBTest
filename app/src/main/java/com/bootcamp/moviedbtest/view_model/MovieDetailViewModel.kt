package com.bootcamp.moviedbtest.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.recyclerview.selection.SelectionTracker
import com.bootcamp.api_service.usecase.GetGenreUseCase
import com.bootcamp.api_service.usecase.GetMovieReviewPagingUseCase
import com.bootcamp.api_service.usecase.MovieDetailUseCase
import com.bootcamp.common.common.BaseViewModel
import com.bootcamp.common.entity.base_response.AppResponse
import com.bootcamp.common.entity.genre.Genre
import com.bootcamp.common.entity.movie_detail.MovieDetailResponse
import com.bootcamp.common.entity.movie_review.MovieReviewResponse
import com.bootcamp.common.entity.movie_review.Result
import com.bootcamp.common.entity.movie_video.MovieVideoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    application: Application,
    val movieGenreUseCase: MovieDetailUseCase,
    val reviewPagingUseCase: GetMovieReviewPagingUseCase
) : BaseViewModel(application) {

    val movieDetailLiveData =
        MutableLiveData<AppResponse<Pair<MovieDetailResponse, MovieVideoResponse>>>()
    val reviewLiveData = MutableLiveData<PagingData<Result>>()

    fun fetchMovieDetailData(id:Int) {
        viewModelScope.launch {
            movieGenreUseCase(id).collect {
                movieDetailLiveData.postValue(it)
            }
        }
        viewModelScope.launch {
            reviewPagingUseCase(id).collect{
                reviewLiveData.postValue(it)
            }
        }
    }

}