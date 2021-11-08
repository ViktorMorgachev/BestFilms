package com.pet.bestfilms.view.films

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pet.bestfilms.R
import com.pet.bestfilms.databinding.FragmentPopularFilmsBinding
import com.pet.bestfilms.utils.gone
import com.pet.bestfilms.utils.hideKeyboard
import com.pet.bestfilms.utils.visible
import com.pet.bestfilms.view.films.viewmodel.FilmsViewModel
import com.pet.bestfilms.view.films.viewmodel.GridSpacingItemDecoration
import com.pet.bestfilms.view.films.viewmodel.PopularFilmsResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilmsFragment : Fragment() {

    private val viewModel: FilmsViewModel by viewModels()

    private val binding by viewBinding(FragmentPopularFilmsBinding::bind)

    private fun setupFilmsList() {
        val spanCount = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 4
            else -> 2
        }
        binding.filmsList.apply {
            layoutManager = GridLayoutManager(activity, spanCount)
            adapter = null
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount,
                    resources.getDimension(R.dimen.itemsDist).toInt(),
                    true
                )
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        recyclerView.hideKeyboard()
                    }
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilmsList()
        lifecycleScope.launch {
            viewModel.popularFilmsResult.collect{state -> handleFilmsList(state)}
        }
    }

    private fun handleFilmsList(state: PopularFilmsResult) {
        when (state) {
            is PopularFilmsResult.SuccessResult -> {
                hideLoading()
                binding.filmsPlaceholder.gone()
                binding.filmsList.visible()
                //.submitList(state.result)
            }
            is PopularFilmsResult.ErrorResult -> {
                hideLoading()
              //  hideAndSetEmptyList()
              //  binding.filmsPlaceholder.setText()
               // Timber.e("Something went wrong.", state.e)
            }
            is PopularFilmsResult.EmptyResult -> {
                hideLoading()
                hideAndSetEmptyList()
                //binding.filmsPlaceholder.setText()
            }
            is PopularFilmsResult.Loading -> showLoading()
        }
    }

    private fun showLoading() {

    }

    private fun hideAndSetEmptyList() {

    }

    private fun hideLoading() {
    }

}