package com.marito.rappitest.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.marito.rappitest.R
import com.marito.rappitest.ui.MoviesFragment

class TabAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PopularMovies -> MoviesFragment.newInstance(PopularMovies)
            TopRatedMovies -> MoviesFragment.newInstance(TopRatedMovies)
            UpcomingMovies -> MoviesFragment.newInstance(UpcomingMovies)
            else -> MoviesFragment.newInstance(PopularMovies)
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            PopularMovies -> context.getString(R.string.popular)
            TopRatedMovies -> context.getString(R.string.top_rated)
            UpcomingMovies -> context.getString(R.string.upcoming)
            else -> null
        }
    }

    companion object {
        const val PopularMovies = 0
        const val TopRatedMovies = 1
        const val UpcomingMovies = 2
    }
}