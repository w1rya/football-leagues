package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.FavoriteAdapter
import com.wiryatech.footballleagues.db.Favorite
import com.wiryatech.footballleagues.db.*
import com.wiryatech.footballleagues.ui.activities.MatchActivity
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteFragment : Fragment() {

    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var favAdapter: FavoriteAdapter

    override fun onResume() {
        super.onResume()

        showFavorite()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favAdapter = FavoriteAdapter(favorites) {
            startActivity<MatchActivity>(MatchActivity.EXTRA_EVENT to it.eventId)
        }

        initUI()
        showFavorite()

        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    private fun initUI() {
        rv_fav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favAdapter
        }
    }

    private fun showFavorite() {
        favorites.clear()
        context?.db?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            favAdapter.notifyDataSetChanged()
        }
    }
}