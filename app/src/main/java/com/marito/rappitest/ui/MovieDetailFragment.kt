package com.marito.rappitest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.marito.rappitest.R
import com.marito.rappitest.databinding.FragmentMovieDetailBinding
import com.marito.rappitest.util.Constants
import com.marito.rappitest.util.Injection
import com.marito.rappitest.viewmodels.MovieDetailViewModel
import com.squareup.picasso.Picasso


const val TAG = "MovieDetailFragment"

class MovieDetailFragment : Fragment(), YouTubePlayer.OnInitializedListener {
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private var youTubePlayer: YouTubePlayer? = null

    private var videoId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        initYouTube()

        binding.playButton.setOnClickListener { view ->
            view.visibility = View.GONE
            if (videoId != null) {
                youTubePlayer?.loadVideo(videoId)
            }
        }

        val movieId = MovieDetailFragmentArgs.fromBundle(arguments!!).movieId

        movieDetailViewModel =
            ViewModelProviders.of(this as Fragment, Injection.provideMovieDetailFactory(context!!, movieId))
                .get(MovieDetailViewModel::class.java)
        movieDetailViewModel.movie.observe(this, Observer { movie ->
            binding.movie = movie
            if (movie.posterPath != null) {
                Picasso.get().load(Constants.tmdbImageBaseUrl + movie.posterPath).into(binding.poster)
            }
        })

        movieDetailViewModel.videos.observe(this, Observer { videos ->
            for (video in videos) {
                if (video.key.isNotEmpty()) {
                    videoId = video.key
                    break
                }
            }
        })

        return binding.root
    }

    private fun initYouTube() {
        val youTubePlayerFragment = YouTubePlayerSupportFragment()
        @Suppress("CAST_NEVER_SUCCEEDS")
        childFragmentManager.beginTransaction().replace(R.id.player, youTubePlayerFragment as Fragment).commit()
        youTubePlayerFragment.initialize(Constants.youtubeApiKey, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        Log.i("Detail", "YouTube Player onInitializationSuccess")

        // Don't do full screen
        player?.setFullscreen(false)
        if (!wasRestored) {
            youTubePlayer = player!!
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

    }
}
