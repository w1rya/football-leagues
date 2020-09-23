package com.wiryatech.footballleagues.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.ui.fragment.NextMatchFragment
import com.wiryatech.footballleagues.ui.fragment.PrevMatchFragment

class SectionsPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    var id = "test"

    companion object {
        const val ID = "id"
    }

    private val tabTitles = intArrayOf(
        R.string.prev_match,
        R.string.next_match
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = PrevMatchFragment()
                val bundle = Bundle()
                bundle.putString(ID, getData())
                fragment.arguments = bundle
                Log.d("BundleFragmentVP", fragment.arguments.toString())
            }
            1 -> {
                fragment = NextMatchFragment()
                val bundle = Bundle()
                bundle.putString(ID, getData())
                fragment.arguments = bundle
                Log.d("BundleFragmentVP1", fragment.arguments.toString())
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int {
        return 2
    }

    fun setData(id: String) {
        this.id = id
    }

    private fun getData(): String {
        return id
    }

}