package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.FavTeamsAdapter
import com.wiryatech.footballleagues.db.FavoriteMatch
import com.wiryatech.footballleagues.db.FavoriteTeams
import com.wiryatech.footballleagues.favorite.FavoritePresenter
import com.wiryatech.footballleagues.favorite.FavoriteView
import com.wiryatech.footballleagues.ui.activities.TeamActivity
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.fragment_fav_teams.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavTeamsFragment : Fragment(), FavoriteView {

    private var favoriteTeams: MutableList<FavoriteTeams> = mutableListOf()
    private lateinit var favAdapter: FavTeamsAdapter
    private lateinit var presenter: FavoritePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favAdapter = FavTeamsAdapter(favoriteTeams) {
            startActivity<TeamActivity>(TeamActivity.EXTRA_TEAM to it.teamId)
        }

        presenter = FavoritePresenter(this)

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
        favoriteTeams.clear()
        presenter.showFavoriteTeams(requireContext())
        favAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
        progressBar.invisible()
    }

    override fun showFavMatch(data: List<FavoriteMatch>) {
        // not used
    }

    override fun showFavTeams(data: List<FavoriteTeams>) {
        favoriteTeams.addAll(data)
    }

    override fun showNoData() {
        iv_error.visible()
        tv_error.visible()
    }
}