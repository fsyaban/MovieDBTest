package com.bootcamp.moviedbtest.fragment.discover

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.bootcamp.common.common.BaseFragment
import com.bootcamp.moviedbtest.R
import com.bootcamp.moviedbtest.databinding.LayoutDiscoverMovieBinding
import com.bootcamp.moviedbtest.view_model.DiscoverMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment:BaseFragment<DiscoverMovieViewModel,LayoutDiscoverMovieBinding>() {
    override val vm: DiscoverMovieViewModel by viewModels()
    val navArgs by navArgs<DiscoverFragmentArgs>()
    val adapter = DiscoverMovieAdapter(){
        vm.navigationtEvent.postValue(DiscoverFragmentDirections.discoverToDetail(it))
    }
    val adapterStateFoot = DiscoverMovieStateAdapter()
    override val layoutResourceId: Int = R.layout.layout_discover_movie

    override fun initBinding(binding: LayoutDiscoverMovieBinding) {
        super.initBinding(binding)
        binding.recycler.adapter=adapter.withLoadStateFooter(adapterStateFoot)
        vm.getDiscover(navArgs.genres)
        vm.discoverMovieLiveData.observe(this){
            CoroutineScope(Dispatchers.Main).launch{
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error || it.append is LoadState.Error
                || it.prepend is LoadState.Error) {
                binding.retryButton.visibility = View.VISIBLE
                binding.recycler.visibility = View.INVISIBLE
                binding.loadingView.visibility = View.INVISIBLE
                binding.retryButton.setOnClickListener {
                    vm.getDiscover(navArgs.genres)
                }
            }else if(it.refresh is LoadState.Loading){
                binding.retryButton.visibility = View.GONE
                binding.recycler.visibility = View.GONE
                binding.loadingView.visibility = View.VISIBLE
            }
            else {
                binding.loadingView.visibility = View.INVISIBLE
                binding.retryButton.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
            }

        }
    }
}