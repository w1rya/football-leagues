package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.FavMatchAdapter
import com.wiryatech.footballleagues.db.FavoriteMatch
import com.wiryatech.footballleagues.db.db
import com.wiryatech.footballleagues.ui.activities.MatchActivity
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.fragment_fav_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavMatchFragment : Fragment() {

    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var favAdapter: FavMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favAdapter = FavMatchAdapter(favoriteMatches) {
            startActivity<MatchActivity>(MatchActivity.EXTRA_EVENT to it.eventId)
        }

        initUI()
        showFavorite()

        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()

        showFavorite()
    }

    private fun initUI() {
        rv_fav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favAdapter
        }
    }

    private fun showFavorite() {
        favoriteMatches.clear()
        context?.db?.use {
            swipeRefresh.isRefreshing = false

            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
            val favorite = result.parseList(classParser<FavoriteMatch>())

            if (favorite.isNullOrEmpty()) {
                iv_error.visible()
                tv_error.visible()
            } else {
                favoriteMatches.addAll(favorite)
            }

            favAdapter.notifyDataSetChanged()
        }
    }
}