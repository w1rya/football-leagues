package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.MatchAdapter
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.matches.MatchListPresenter
import com.wiryatech.footballleagues.matches.MatchListView
import com.wiryatech.footballleagues.models.Match
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.toast

class NextMatchFragment : Fragment(), MatchListView {

    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var idLeague: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MatchAdapter(matches) {
            context?.toast(it.idEvent)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchListPresenter(this, request, gson)

        initUI()

        Log.d("BundleFragment1", "$savedInstanceState, $arguments")
        if (arguments != null) {
            idLeague = arguments?.getString(PrevMatchFragment.ID).toString()
            Log.d("Next", "$arguments, $idLeague")
            presenter.getNextMatch(idLeague)
        }

        swipeRefresh.setOnRefreshListener {
            presenter.getNextMatch(idLeague)
        }
    }

    private fun initUI() {
        rv_next_match.layoutManager = LinearLayoutManager(context)
        rv_next_match.adapter = adapter
    }

    override fun showLoading() {
        progressBarNext.visible()
    }

    override fun hideLoading() {
        progressBarNext.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }
}