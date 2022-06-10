package com.bootcamp.moviedbtest.fragment.movie_detail

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bootcamp.api_service.Const
import com.bootcamp.common.common.BaseFragment
import com.bootcamp.moviedbtest.R
import com.bootcamp.moviedbtest.databinding.MovieDetailLayoutBinding
import com.bootcamp.moviedbtest.view_model.MovieDetailViewModel
import com.bootcamp.moviedbtest.fragment.movie_detail.MovieVideoAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<MovieDetailViewModel, MovieDetailLayoutBinding>() {
    override val vm: MovieDetailViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.movie_detail_layout
    val navArgs by navArgs<MovieDetailFragmentArgs>()
    val reviewAdapter = MovieReviewAdapter()
    val movieVideoAdapter = MovieVideoAdapter(::showVideo)

    override fun initBinding(binding: MovieDetailLayoutBinding) {
        super.initBinding(binding)
        binding.rvReview.adapter = reviewAdapter
        binding.rvVideo.adapter = movieVideoAdapter
        vm.fetchMovieDetailData(navArgs.movieId)
        observeLiveData()
    }

    fun observeLiveData() {
        observeResponseData(vm.movieDetailLiveData, success = {
            binding.movieDetailView.visibility = View.VISIBLE
            binding.proggress.visibility = View.INVISIBLE
            binding.retryButton.visibility = View.INVISIBLE

            binding.data = it.first
            binding.genre.text = it.first.genres.joinToString(separator = ", "){ it.name }
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${it.first.backdropPath}")
                .into(binding.imgBackground)
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${it.first.posterPath}")
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(24)))
                .into(binding.imgPoster)
            if(it.second.results.isNotEmpty()){
                showVideo(it.second.results[0].key)
            }

            movieVideoAdapter.differ.submitList(it.second.results)
        }, error = {
            binding.movieDetailView.visibility = View.GONE
            binding.retryButton.visibility = View.VISIBLE
            binding.proggress.visibility = View.GONE
            binding.retryButton.setOnClickListener {
                vm.fetchMovieDetailData(navArgs.movieId)
            }
        }, loading = {
            binding.proggress.visibility=View.VISIBLE
            binding.retryButton.visibility = View.INVISIBLE
            binding.movieDetailView.visibility=View.INVISIBLE
        })
        vm.reviewLiveData.observe(this){
            CoroutineScope(Dispatchers.Main).launch{
                reviewAdapter.submitData(it)
            }
        }
    }


    fun showVideo(videoId:String){
        val youtubeFragment = YouTubePlayerSupportFragmentX.newInstance()
        with(parentFragmentManager){
            beginTransaction().apply {
                replace(R.id.fragmentVideoTrailer,youtubeFragment)
                commit()
            }
        }
        youtubeFragment.initialize("AIzaSyAUGz7-k_vZw9l0wxkH6ljA1GyfeArhgnY",
            object : YouTubePlayerSupportFragmentX.OnInitializedListener(){
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer?,
                    p2: Boolean
                ) {
                    p1?.cueVideo(videoId)
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    Log.e("youtubePlayer","error ${p1?.name}")
                }
            })
    }
}