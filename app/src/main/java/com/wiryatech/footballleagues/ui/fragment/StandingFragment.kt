package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.StandingAdapter
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.models.Standing
import com.wiryatech.footballleagues.standing.StandingPresenter
import com.wiryatech.footballleagues.standing.StandingView
import com.wiryatech.footballleagues.utils.Constants
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.fragment_standing.*

class StandingFragment : Fragment(), StandingView {

    private var standing: MutableList<Standing> = mutableListOf()
    private lateinit var presenter: StandingPresenter
    private lateinit var standingAdapter: StandingAdapter
    private lateinit var idLeague: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_standing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        standingAdapter = StandingAdapter(standing)

        val request = ApiRepository()
        val gson = Gson()
        presenter = StandingPresenter(this, request, gson)

        initUI()

        if (arguments != null) {
            idLeague = arguments?.getString(MatchFragment.ID).toString()
            Log.d("Standing", "$arguments, $idLeague")
            presenter.getStanding(idLeague)
        }

        swipeRefresh.setOnRefreshListener {
            presenter.getStanding(idLeague)
        }
    }

    private fun initUI() {
        rv_standing.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = standingAdapter
        }
    }

    override fun showLoading() {
        progressBarStanding.visible()
    }

    override fun hideLoading() {
        progressBarStanding.invisible()
    }

    override fun showStanding(data: List<Standing>) {
        iv_error.invisible()
        tv_error.invisible()

        swipeRefresh.isRefreshing = false
        standing.clear()
        standing.addAll(data)
        standingAdapter.notifyDataSetChanged()
    }

    override fun showError(code: Int) {
        rv_standing.invisible()

        when (code) {
            Constants.NO_CONNECTION -> showNoConnection()
            Constants.STANDING_NULL -> showNoData()
        }
    }

    private fun showNoConnection() {
        swipeRefresh.isRefreshing = false
        iv_error.setImageResource(R.drawable.ic_no_signal)
        tv_error.text = getString(R.string.no_connection)
        iv_error.visible()
        tv_error.visible()
    }

    private fun showNoData() {
        swipeRefresh.isRefreshing = false
        iv_error.setImageResource(R.drawable.ic_no_data)
        tv_error.text = getString(R.string.no_data)
        iv_error.visible()
        tv_error.visible()
    }

}