package com.leandroid.apps.cinemalistings.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandroid.apps.cinemalistings.R
import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.data.repository.MovieRepository
import com.leandroid.apps.cinemalistings.databinding.FragmentHomeBinding
import com.leandroid.apps.cinemalistings.model.MovieDao
import com.leandroid.apps.cinemalistings.model.MovieDataBase
import com.leandroid.apps.cinemalistings.ui.MovieListener
import com.leandroid.apps.cinemalistings.ui.details.DetailsMovieActivity
import com.leandroid.apps.cinemalistings.ui.utils.ComponentUtils.Companion.showToast

class HomeFragment : Fragment(), MovieListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterPopular: HomeAdapter
    private lateinit var  repository: MovieRepository
    private lateinit var viewModel: HomeViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        subscribeLiveData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initViewModel() {
        context?.let {
            repository = MovieRepository(APIManager(), MovieDataBase.getDataBase(it).movieDao())

            HomeViewModelFactory(repository).run {
                viewModel = ViewModelProvider(requireActivity(), this)[HomeViewModel::class.java]
            }
        }
    }

    private fun subscribeLiveData() {
        with(viewModel) {
            getMovies()
            isLoading.observe(viewLifecycleOwner) {
                handlerProgressBar(it)
                handlerRecyclerVisibility(!it)
            }
            isError.observe(viewLifecycleOwner) {
                if (it) {
                    showError()
                }
            }
            movies.observe(viewLifecycleOwner) {
                adapterPopular.setMovies(it)
            }
        }
    }

    private fun showError() {
        showToast(requireContext(), getString(R.string.error_toast))
    }

    private fun initRecyclerView() {
        adapterPopular = HomeAdapter(this)
        binding.moviesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterPopular
        }
    }

    private fun handlerProgressBar(show: Boolean) {
        binding.iProgressBar.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun handlerRecyclerVisibility(show: Boolean){
        binding.moviesRecycler.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onClick(id: String) {
        val intent = Intent(requireContext(), DetailsMovieActivity::class.java)
        intent.putExtra(DetailsMovieActivity.ID_MOVIE, id)
        startActivity(intent)
    }
}