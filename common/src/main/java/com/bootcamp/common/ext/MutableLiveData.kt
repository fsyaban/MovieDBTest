package com.bootcamp.common.ext

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.toggle(t: T) {
    val data = this.value.orEmpty()
    val ar = arrayListOf<T>().apply { this.addAll(data) }
    ar.toggle(t)
    value = ar
}


fun <T> MutableLiveData<List<T>>.size(): Int {
    val data = this.value.orEmpty()
    return data.size
}

fun <T> MutableLiveData<List<T>>.isEmpty(): Boolean {
    val data = this.value.orEmpty()
    return data.isEmpty()
}
fun <T> MutableLiveData<List<T>>.clear() {
    this.value = arrayListOf()
}