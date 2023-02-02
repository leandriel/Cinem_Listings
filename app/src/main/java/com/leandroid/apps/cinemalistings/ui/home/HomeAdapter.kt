package com.leandroid.apps.cinemalistings.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.apps.cinemalistings.R
import com.leandroid.apps.cinemalistings.model.Movie
import com.leandroid.apps.cinemalistings.ui.MovieListener

class HomeAdapter(private val listener: MovieListener):RecyclerView.Adapter<MovieViewHolder>() {
    private var movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie,parent,false),listener)
    }

    override fun onBindViewHolder(holderMovie: MovieViewHolder, position: Int) {
        val item = movies[position]
        holderMovie.bind(item)
    }

    override fun getItemCount(): Int = movies.size

    fun setMovies(movies: MutableList<Movie>){
        this.movies = movies
        notifyDataSetChanged()
    }
}