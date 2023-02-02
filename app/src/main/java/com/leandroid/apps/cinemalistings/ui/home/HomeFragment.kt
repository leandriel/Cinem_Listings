package com.leandroid.apps.cinemalistings.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandroid.apps.cinemalistings.R
import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.data.repository.MovieRepository
import com.leandroid.apps.cinemalistings.databinding.FragmentHomeBinding
import com.leandroid.apps.cinemalistings.model.MovieDataBase
import com.leandroid.apps.cinemalistings.ui.MovieListener
import com.leandroid.apps.cinemalistings.ui.details.DetailsMovieActivity
import com.leandroid.apps.cinemalistings.ui.utils.ComponentUtils.Companion.showToast

class HomeFragment : Fragment(), MovieListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterPopular: HomeAdapter
    private lateinit var  repository: MovieRepository
    private lateinit var viewModel: HomeViewModel

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isWritePermissionGranted = false
    private var isLocationPermissionGranted = false
    private var isCameraPermissionGranted = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        subscribeLiveData()

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){permissions ->
            isWritePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted
            isCameraPermissionGranted = permissions[android.Manifest.permission.CAMERA] ?: isCameraPermissionGranted
            isLocationPermissionGranted = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranted
        }

        requestPermission()


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


    private fun requestPermission()
    {
        isWritePermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE

        )== PackageManager.PERMISSION_GRANTED

        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA

        )== PackageManager.PERMISSION_GRANTED

        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION

        )== PackageManager.PERMISSION_GRANTED

        val permissionRequest: MutableList<String> = ArrayList()

        if(!isWritePermissionGranted){
            permissionRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(!isCameraPermissionGranted){
            permissionRequest.add(android.Manifest.permission.CAMERA)
        }

        if(!isLocationPermissionGranted){
            permissionRequest.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if(permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }
}