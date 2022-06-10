package com.bootcamp.moviedbtest.fragment.genre

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import com.bootcamp.common.common.BaseFragment
import com.bootcamp.moviedbtest.R
import com.bootcamp.moviedbtest.databinding.GenreLayoutBinding
import com.bootcamp.moviedbtest.view_model.GenreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment : BaseFragment<GenreViewModel, GenreLayoutBinding>() {
    override val vm: GenreViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.genre_layout
    val adapter = GenreAdapter({ vm.selectionTracker?.selection?.toMutableList().orEmpty() })

    override fun initBinding(binding: GenreLayoutBinding) {
        super.initBinding(binding)
        binding.recycler.adapter = adapter
        binding.button.setOnClickListener {
            vm.navigationtEvent.postValue(
                GenreFragmentDirections.genreToDiscover(
                    vm.selectionTracker?.selection?.toMutableList().orEmpty()
                        .joinToString(separator = ",")
                )
            )
        }

        observeLiveData()

        vm.selectionTracker = vm.selectionTracker?.let {
            createSelectionTracker().apply {
                it.selection.forEach {
                    select(it)
                }
            }
        } ?: kotlin.run {
            createSelectionTracker()
        }
    }

    fun observeLiveData(){
        observeResponseData(vm.data, success = {
            binding.recycler.visibility= View.VISIBLE
            binding.retryButton.visibility= View.GONE
            binding.loadingView.visibility=View.GONE
            adapter.data.submitList(it)
        }, error = {
            binding.recycler.visibility = View.GONE
            binding.retryButton.visibility = View.VISIBLE
            binding.loadingView.visibility = View.INVISIBLE
            binding.retryButton.setOnClickListener {
                vm.fetchGenreData()
            }
        }, loading = {
            binding.recycler.visibility = View.INVISIBLE
            binding.retryButton.visibility = View.GONE
            binding.loadingView.visibility = View.VISIBLE
        })
    }
    fun createSelectionTracker(): SelectionTracker<Long> {
        return SelectionTracker.Builder<Long>(
            GenreFragment::class.java.name,
            binding.recycler,
            GenreKeyProvider(adapter),
            GenreItemLookUp(binding.recycler),
            StorageStrategy.createLongStorage()
        ).withOnItemActivatedListener { a, e ->
            a.selectionKey?.let {
                vm.selectionTracker?.select(it)
            }
            true
        }.withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
    }


}