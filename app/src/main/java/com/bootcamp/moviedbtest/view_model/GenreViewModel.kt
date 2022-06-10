package com.bootcamp.moviedbtest.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.SelectionTracker
import com.bootcamp.api_service.usecase.GetGenreUseCase
import com.bootcamp.api_service.usecase.MovieDetailUseCase
import com.bootcamp.common.common.BaseViewModel
import com.bootcamp.common.entity.base_response.AppResponse
import com.bootcamp.common.entity.genre.Genre
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    application: Application,
    val genreUseCase: GetGenreUseCase
) : BaseViewModel(application) {

    val data = MutableLiveData<AppResponse<List<Genre>>>()
    var selectionTracker : SelectionTracker<Long>? = null

    init {
        fetchGenreData()
    }

    fun fetchGenreData(){
        viewModelScope.launch {
            genreUseCase().collect(){
                data.postValue(it)
            }
        }
    }



}