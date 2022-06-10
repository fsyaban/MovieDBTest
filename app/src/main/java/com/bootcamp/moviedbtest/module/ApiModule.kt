package com.bootcamp.moviedbtest.module

import android.content.Context
import com.bootcamp.api_service.RetrofitClient
import com.bootcamp.api_service.service.DiscoverMovieService
import com.bootcamp.api_service.service.GenreService
import com.bootcamp.api_service.service.MovieDetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context) = RetrofitClient.getClient(context)

    @Provides
    @Singleton
    fun provideGenreService(retrofit: Retrofit) =
        retrofit.create(GenreService::class.java)

    @Provides
    @Singleton
    fun provideDiscoverMovieService(retrofit: Retrofit) =
        retrofit.create(DiscoverMovieService::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailService(retrofit: Retrofit) =
        retrofit.create(MovieDetailService::class.java)


}