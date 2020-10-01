package com.wiryatech.footballleagues.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.ui.fragment.FavMatchFragment
import com.wiryatech.footballleagues.ui.fragment.FavTeamsFragment

class FavPagerAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val tabTitles = intArrayOf(
        R.string.match,
        R.string.teams
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavMatchFragment()
            1 -> fragment = FavTeamsFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? = context.resources.getString(tabTitles[position])

    override fun getCount(): Int = tabTitles.size

}