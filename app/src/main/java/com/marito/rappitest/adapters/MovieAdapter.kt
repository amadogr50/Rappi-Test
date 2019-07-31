package com.marito.rappitest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marito.rappitest.databinding.ItemMovieBinding
import com.marito.rappitest.models.Movie
import com.marito.rappitest.util.Constants
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val context: Context,
    private val clickListener: MovieAdapterListener
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(MovieAdapterDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)

        Picasso.get().load(Constants.tmdbImageBaseUrl + movie.posterPath).into(holder.binding.poster)

        holder.binding.movie = movie
        holder.binding.root.setOnClickListener { clickListener.onClick(movie) }
    }

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
}

class MovieAdapterListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}

class MovieAdapterDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}