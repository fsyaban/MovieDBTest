package com.bootcamp.moviedbtest.fragment.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.common.databinding.BaseStateLayoutBinding

class DiscoverMovieStateAdapter :
    LoadStateAdapter<DiscoverMovieStateAdapter.DiscoverMovieStateViewHolder>() {

    inner class DiscoverMovieStateViewHolder(
        val binding: BaseStateLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.Error -> {
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.loadingContainer.visibility = View.GONE
                    binding.errorContainer.setOnClickListener {

                    }
                }
                is LoadState.Loading -> {
                    binding.loadingContainer.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                }
                is LoadState.NotLoading -> {
                    binding.loadingContainer.visibility = View.GONE
                    binding.errorContainer.visibility = View.GONE
                }
            }
        }
    }

    override fun onBindViewHolder(holder: DiscoverMovieStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DiscoverMovieStateViewHolder {
        return DiscoverMovieStateViewHolder(
            BaseStateLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        ).apply {
            bind(loadState)
        }
    }

}

