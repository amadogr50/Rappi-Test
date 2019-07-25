package com.marito.rappitest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marito.rappitest.databinding.FragmentMovieDetailBinding
import com.marito.rappitest.util.Constants
import com.marito.rappitest.util.Injection
import com.marito.rappitest.viewmodels.MovieDetailFactory
import com.marito.rappitest.viewmodels.MovieDetailViewModel
import com.squareup.picasso.Picasso

class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val movieId = MovieDetailFragmentArgs.fromBundle(arguments!!).movieId

        movieDetailViewModel = ViewModelProviders.of(this, Injection.provideMovieDetailFactory(context!!, movieId)).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.movie.observe(this, Observer { movie ->
            binding.movie = movie

            Picasso.get().load(Constants.tmdbImageBaseUrl + movie.posterPath).into(binding.poster)
        })

        return binding.root
    }

}
