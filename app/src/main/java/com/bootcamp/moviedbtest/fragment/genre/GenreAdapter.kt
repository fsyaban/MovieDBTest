package com.bootcamp.moviedbtest.fragment.genre

import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bootcamp.common.entity.genre.Genre
import com.bootcamp.moviedbtest.databinding.GenreItemBinding

class GenreAdapter(
    val getSelection: () -> List<Long>
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    init {

        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = data.currentList[position].id.toLong()

    inner class GenreViewHolder(val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun getItemDetails() = object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = bindingAdapterPosition

            override fun getSelectionKey(): Long? =
                data.currentList[bindingAdapterPosition].id.toLong()
        }

        fun bind(genre: Genre) {
            binding.isSelected = getSelection().contains(genre.id.toLong())
            binding.data = genre
        }
    }

    val data = AsyncListDiffer(this, itemCallback)

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            GenreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(data.currentList[position])

    }

    override fun getItemCount(): Int = data.currentList.size

}

class GenreKeyProvider(val genreAdapter: GenreAdapter) : ItemKeyProvider<Long>(SCOPE_MAPPED) {
    override fun getKey(position: Int): Long? = genreAdapter.data.currentList[position].id.toLong()

    override fun getPosition(key: Long): Int =
        genreAdapter.data.currentList.indexOfFirst { key == it.id.toLong() }
}

class GenreItemLookUp(val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? =
        recyclerView.findChildViewUnder(e.x, e.y)?.let {
            (recyclerView.getChildViewHolder(it) as GenreAdapter.GenreViewHolder).getItemDetails()
        }


}
