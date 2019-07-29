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
import com.marito.rappitest.adapters.MovieAdapter
import com.marito.rappitest.adapters.MovieAdapterListener
import com.marito.rappitest.databinding.FragmentMoviesBinding
import com.marito.rappitest.util.Injection
import com.marito.rappitest.viewmodels.SearchMoviesViewModel

private const val QUERY = "query"

class SearchMoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesList: RecyclerView
    private lateinit var searchMoviesViewModel: SearchMoviesViewModel
    private lateinit var movieAdapter: MovieAdapter
    private var query: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(QUERY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMoviesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        movieAdapter = MovieAdapter(context!!, MovieAdapterListener { movie ->
            val action =
                SearchMoviesFragmentDirections.actionSearchMoviesFragmentToMovieDetailFragment(movie.id, movie.title)
            findNavController().navigate(action)
        })

        moviesList = binding.movies
        moviesList.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        setupScrollListener()

        searchMoviesViewModel = ViewModelProviders.of(this, Injection.provideSearchMoviesFactory(context!!, query!!))
            .get(SearchMoviesViewModel::class.java)

        searchMoviesViewModel.movies.observe(this, Observer { movies ->
            movieAdapter.submitList(movies)
        })

        return binding.root
    }

    private fun setupScrollListener() {
        val layoutManager = moviesList.layoutManager as LinearLayoutManager
        moviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                searchMoviesViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    companion object {
        fun newInstance(query: String) =
            SearchMoviesFragment().apply {
                arguments = Bundle().apply {
                    putString(QUERY, query)
                }
            }
    }
}
