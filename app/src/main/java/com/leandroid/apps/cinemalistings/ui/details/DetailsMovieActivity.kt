package com.leandroid.apps.cinemalistings.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.leandroid.apps.cinemalistings.R
import com.leandroid.apps.cinemalistings.data.api.APIManager
import com.leandroid.apps.cinemalistings.data.repository.DetailsRepository
import com.leandroid.apps.cinemalistings.databinding.ActivityDetailsMovieBinding
import com.leandroid.apps.cinemalistings.ui.utils.ComponentUtils.Companion.showToast
import com.squareup.picasso.Picasso

class DetailsMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private val repository = DetailsRepository(APIManager())
    lateinit var binding: ActivityDetailsMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id: Int = intent.getIntExtra(ID_MOVIE,0)
        initViewModel()
        subscribeLiveData()
        viewModel.getDetailsMovie(id.toString())
    }

    private fun initViewModel(){
        DetailsViewModelFactory(repository).run {
            viewModel = ViewModelProvider(this@DetailsMovieActivity, this)[DetailsViewModel::class.java]
        }
    }

    private fun subscribeLiveData(){
        with(viewModel){
            isLoading.observe(this@DetailsMovieActivity) {
                handlerProgressBar(it)
                handlerContainerVisibility(!it)
            }

            isError.observe(this@DetailsMovieActivity){
                if(it){
                    showToast(this@DetailsMovieActivity,getString(R.string.error_toast))
                    finish()
                }
            }

            movie.observe(this@DetailsMovieActivity) { m ->
                with(binding){
                    tvTitle.text = m.fullTitle
                    Glide.with(this@DetailsMovieActivity)
                        .load(m.urlImage)
                        .into(movieImage)
                    tvReleaseStateDetail.text = String.format(getString(R.string.release_state),m.releaseState)
                    tvGenres.text = String.format(getString(R.string.genres),m.genres)
                    tvImdbRating.text = String.format(getString(R.string.rating_imdb),m.imDbRating)
                    tvPlot.text = String.format(getString(R.string.plot),m.plot)
                }
            }
        }
    }
    private fun handlerProgressBar(show: Boolean) {
        binding.iProgressBar.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun handlerContainerVisibility(show: Boolean){
        binding.clContainer.visibility = if (show) View.VISIBLE else View.GONE
    }
    companion object {
        const val ID_MOVIE = "id_movie"
    }
}