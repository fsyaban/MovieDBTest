package com.bootcamp.moviedbtest.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bootcamp.api_service.usecase.GetDiscoverMoviePagingUseCase
import com.bootcamp.common.common.BaseViewModel
import com.bootcamp.common.entity.discover_movie.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverMovieViewModel @Inject constructor(
    application: Application,
    val useCase: GetDiscoverMoviePagingUseCase
) : BaseViewModel(application) {

    val discoverMovieLiveData = MutableLiveData<PagingData<Result>>()

    fun getDiscover(genreList:String?){
        viewModelScope.launch {
            useCase(genreList).cachedIn(viewModelScope).collect {
                discoverMovieLiveData.postValue(it)
            }
        }
    }


}