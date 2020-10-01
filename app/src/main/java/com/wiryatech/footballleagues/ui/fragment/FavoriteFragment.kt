package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.FavPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    private lateinit var favPagerAdapter: FavPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManager = requireActivity().supportFragmentManager
        favPagerAdapter = FavPagerAdapter(requireContext(), fragmentManager)
        vp_fav.adapter = favPagerAdapter
        tab_fav.setupWithViewPager(vp_fav)
    }

}