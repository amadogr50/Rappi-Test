package com.marito.rappitest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marito.rappitest.adapters.TabAdapter
import com.marito.rappitest.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)

        binding.container.apply {
            adapter = TabAdapter(context!!, childFragmentManager)
        }

        binding.tabs.setupWithViewPager(binding.container)

        return binding.root
    }


}
