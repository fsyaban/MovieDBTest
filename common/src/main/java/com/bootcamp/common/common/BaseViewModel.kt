package com.bootcamp.common.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections
import com.bootcamp.common.entity.base_response.AppResponse
import com.bootcamp.common.entity.base_response.ResponseError
import com.bootcamp.common.entity.base_response.ResponseLoading
import com.bootcamp.common.entity.base_response.ResponseSuccess
import com.bootcamp.common.ui.DialogData
import com.bootcamp.common.ext.SingleLiveEvent

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val navigationtEvent = SingleLiveEvent<NavDirections>()
    val popBackStackEvent = SingleLiveEvent<Any>()
    var parent: BaseViewModel? = null

    fun navigate(nav: NavDirections) {
        navigationtEvent.postValue(nav)
    }

    fun popBackStack() {
        popBackStackEvent.postValue(Any())
    }

}