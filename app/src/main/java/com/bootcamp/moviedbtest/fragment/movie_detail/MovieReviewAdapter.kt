package com.bootcamp.moviedbtest.fragment.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.common.entity.movie_review.Result
import com.bootcamp.moviedbtest.databinding.MovieReviewItemBinding
import com.bumptech.glide.Glide

class MovieReviewAdapter : PagingDataAdapter<Result, MovieReviewAdapter.MovieReviewViewHolder>(
    itemCallback
) {
    inner class MovieReviewViewHolder(
        val binding: MovieReviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root)


    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return false
            }
        }
    }

    override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.data = data
        data?.let {
            it.authorDetails.avatarPath?.let { avatarPath ->
                if (avatarPath.substring(0, 5).equals("/http")) {
                    Glide.with(holder.binding.root).load(avatarPath.substring(1))
                        .into(holder.binding.image)
                } else {
                    Glide.with(holder.binding.root)
                        .load("https://www.themoviedb.org/t/p/w300_and_h300_face${avatarPath}")
                        .into(holder.binding.image)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewViewHolder =
        MovieReviewViewHolder(
            MovieReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

}