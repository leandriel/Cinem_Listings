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

        binding.tvTitle.text = movie.title
        binding.tvReleaseState.text = movie.releaseState
        binding.tvRating.text = movie.imDbRating
        Glide.with(binding.ivPortada.context)
            .load(movie.image)
            .into(binding.ivPortada)
    }
}