package com.leandroid.apps.cinemalistings.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leandroid.apps.cinemalistings.databinding.ItemMovieBinding
import com.leandroid.apps.cinemalistings.model.Movie
import com.leandroid.apps.cinemalistings.ui.MovieListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext

class MovieViewHolder(view: View, private val listener: MovieListener) :
    RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun bind(movie: Movie){
        binding.root.setOnClickListener {
            listener.onClick(movie.id)
        }
        with(binding){
            tvTitle.text = movie.title
            tvReleaseState.text = movie.releaseState
            tvRating.text = movie.imDbRating
            tvGenres.text = movie.genres
            Glide.with(binding.ivPortada.context)
                .load(movie.image)
                .into(binding.ivPortada)
        }


    }
}