package com.bootcamp.moviedbtest.fragment.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.common.entity.movie_video.Result
import com.bootcamp.moviedbtest.databinding.MovieVideoItemBinding
import com.bumptech.glide.Glide

class MovieVideoAdapter(val showVideo:(String)-> Unit) : RecyclerView.Adapter<MovieVideoAdapter.MovieVideoViewHolder>() {

    inner class MovieVideoViewHolder(
        val binding: MovieVideoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Result) {
            binding.tvTitle.text = data.name
            Glide.with(binding.root)
                .load("https://img.youtube.com/vi/" + data.key + "/default.jpg")
                .centerCrop()
                .into(binding.imgPoster)
            binding.root.setOnClickListener {
                showVideo(data.key)
            }
        }
    }

    val differ = AsyncListDiffer(this, itemCallback)

    companion object{
        val itemCallback = object : DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return false
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideoViewHolder =
        MovieVideoViewHolder(
            MovieVideoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: MovieVideoViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}