package com.marito.rappitest.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marito.rappitest.R
import com.marito.rappitest.adapters.MovieAdapter
import com.marito.rappitest.adapters.MovieAdapterListener
import com.marito.rappitest.databinding.FragmentMoviesBinding
import com.marito.rappitest.viewmodels.MoviesFactory
import com.marito.rappitest.viewmodels.MoviesViewModel

private const val KIND = "kind"

class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MovieAdapter
    private var kind: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            kind = it.getInt(KIND)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        movieAdapter = MovieAdapter(context!!, MovieAdapterListener { movie ->
            val action = MainFragmentDirections.actionMainFragmentToMovieDetailFragment(movie.id)
            findNavController().navigate(action)
        })

        binding.movies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        moviesViewModel = ViewModelProviders.of(this, MoviesFactory(kind)).get(MoviesViewModel::class.java)

        moviesViewModel.movies.observe(this, Observer { movies ->
            movieAdapter.submitList(movies)
        })

        return binding.root
    }

    companion object {
        fun newInstance(kind: Int) =
            MoviesFragment().apply {
                arguments = Bundle().apply {
                    putInt(KIND, kind)
                }
            }
    }
}
