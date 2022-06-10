package com.bootcamp.api_service

import android.content.Context
import android.util.Log
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.GanderInterceptor
import com.ashokvarma.gander.imdb.GanderIMDB
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val client by lazy {
        Retrofit.Builder()
            .client(OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor(){
                Log.e("InetLog",it)
            }).build())
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
    fun getClient(context: Context): Retrofit {

        Gander.setGanderStorage(GanderIMDB.getInstance())
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor(){
                Log.e("InetLog",it)
            }).addInterceptor (
                GanderInterceptor(context).showNotification(true)
            ).build())
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}