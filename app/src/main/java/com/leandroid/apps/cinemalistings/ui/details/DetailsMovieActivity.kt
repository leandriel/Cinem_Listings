package com.leandroid.apps.cinemalistings.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.leandroid.apps.cinemalistings.R
import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.data.repository.DetailsRepository
import com.leandroid.apps.cinemalistings.data.repository.MovieRepository
import com.leandroid.apps.cinemalistings.databinding.ActivityDetailsMovieBinding
import com.leandroid.apps.cinemalistings.model.MovieDataBase
import com.leandroid.apps.cinemalistings.ui.utils.ComponentUtils.Companion.showToast
import com.squareup.picasso.Picasso

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var repository: DetailsRepository
    private lateinit var binding: ActivityDetailsMovieBinding
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = intent?.getStringExtra(ID_MOVIE) ?: ""
        initViewModel()
        subscribeLiveData()
    }

    private fun initViewModel() {
        repository = DetailsRepository(MovieDataBase.getDataBase(this).movieDao())
        DetailsViewModelFactory(repository).run {
            viewModel =
                ViewModelProvider(this@DetailsMovieActivity, this)[DetailsViewModel::class.java]
        }
    }

    private fun subscribeLiveData() {
        with(viewModel) {
            getDetailsMovie(id)
            isLoading.observe(this@DetailsMovieActivity) {
                handlerProgressBar(it)
                handlerContainerVisibility(!it)
            }

            isError.observe(this@DetailsMovieActivity) {
                if (it) {
                    showToast(this@DetailsMovieActivity, getString(R.string.error_toast))
                    finish()
                }
            }

            movie.observe(this@DetailsMovieActivity) { m ->
                with(binding) {
                    tvTitle.text = m.fullTitle
                    Glide.with(this@DetailsMovieActivity)
                        .load(m.image)
                        .into(movieImage)
                    tvReleaseStateDetail.text = m.releaseState
                    tvGenres.text = m.genres
                    tvImdbRating.text = m.imDbRating
                    tvPlot.text = m.plot
                }
            }
        }
    }

    private fun handlerProgressBar(show: Boolean) {
        binding.iProgressBar.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun handlerContainerVisibility(show: Boolean) {
        binding.clContainer.visibility = if (show) View.VISIBLE else View.GONE
    }

    companion object {
        const val ID_MOVIE = "id_movie"
    }
}