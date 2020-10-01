package com.wiryatech.footballleagues.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.ui.fragment.*

class SectionsPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    var id = "test"

    companion object {
        const val ID = "id"
    }

    private val tabTitles = intArrayOf(
        R.string.match,
        R.string.standing,
        R.string.teams
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = MatchFragment()
                val bundle = Bundle()
                bundle.putString(ID, getData())
                fragment.arguments = bundle
                Log.d("BundleFragmentVP", fragment.arguments.toString())
            }
            1 -> {
                fragment = StandingFragment()
                val bundle = Bundle()
                bundle.putString(ID, getData())
                fragment.arguments = bundle
                Log.d("fragment standing", fragment.arguments.toString())
            }
            2 -> {
                fragment = TeamsFragment()
                val bundle = Bundle()
                bundle.putString(ID, getData())
                fragment.arguments = bundle
                Log.d("fragment teams", fragment.arguments.toString())
            }
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(tabTitles[position])

    override fun getCount(): Int = tabTitles.size

    fun setData(id: String) {
        this.id = id
    }

    private fun getData(): String {
        return id
    }

}