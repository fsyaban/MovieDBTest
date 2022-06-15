package com.bootcamp.moviedbtest.fragment.movie_detail

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bootcamp.common.common.BaseFragment
import com.bootcamp.moviedbtest.R
import com.bootcamp.moviedbtest.databinding.MovieDetailLayoutBinding
import com.bootcamp.moviedbtest.view_model.MovieDetailViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
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
    val movieVideoAdapter = MovieVideoAdapter(){
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$it")
            )
        )
    }

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
            binding.genre.text = it.first.genres.joinToString(separator = ", ") { it.name }
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${it.first.backdropPath}")
                .into(binding.imgBackground)
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${it.first.posterPath}")
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(24)))
                .into(binding.imgPoster)
            if (it.second.results.isNotEmpty()) {
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
            binding.proggress.visibility = View.VISIBLE
            binding.retryButton.visibility = View.INVISIBLE
            binding.movieDetailView.visibility = View.INVISIBLE
        })
        vm.reviewLiveData.observe(this) {
            CoroutineScope(Dispatchers.Main).launch {
                reviewAdapter.submitData(it)
            }
        }
    }


    fun showVideo(videoId: String) {

        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                youTubePlayer.cueVideo(videoId, 0f)

                val defaultPlayerUiController =
                    DefaultPlayerUiController(binding.fragmentVideoTrailer, youTubePlayer)
                binding.fragmentVideoTrailer.setCustomPlayerUi(defaultPlayerUiController.rootView)
            }
        }

        val option = IFramePlayerOptions.Builder().controls(0).build()
        binding.fragmentVideoTrailer.initialize(listener, option)

    }

}