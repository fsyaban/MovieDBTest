package com.bootcamp.moviedbtest.fragment.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.api_service.Const.getGenreString
import com.bootcamp.common.entity.discover_movie.Result
import com.bootcamp.moviedbtest.databinding.DiscoverMoviesItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


class DiscoverMovieAdapter(val navigateToDetailMovie: (Int) -> Unit) :
    PagingDataAdapter<Result, DiscoverMovieAdapter.DiscoverMovieViewHolder>(
        diffCallback
    ) {
    inner class DiscoverMovieViewHolder(val binding: DiscoverMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)



    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return false
            }
        }
    }

    override fun onBindViewHolder(holder: DiscoverMovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.root.setOnClickListener {
            data?.let { data -> navigateToDetailMovie(data.id) }
        }
        holder.binding.genre.text = data?.genreIds?.joinToString(separator = ", ") { getGenreString(it) }
        holder.binding.data = data
        data?.posterPath?.let {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(42))
            Glide.with(holder.binding.root)
                .load("https://image.tmdb.org/t/p/w500${it}")
                .apply(requestOptions)
                .into(holder.binding.image)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMovieViewHolder {
        return DiscoverMovieViewHolder(
            DiscoverMoviesItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


}