package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.MatchAdapter
import com.wiryatech.footballleagues.api.ApiRepository
import com.wiryatech.footballleagues.matches.MatchListPresenter
import com.wiryatech.footballleagues.matches.MatchListView
import com.wiryatech.footballleagues.models.Match
import com.wiryatech.footballleagues.ui.activities.MatchActivity
import com.wiryatech.footballleagues.utils.invisible
import com.wiryatech.footballleagues.utils.visible
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class SearchFragment : Fragment(), MatchListView {

    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MatchListPresenter
    private lateinit var matchAdapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matchAdapter = MatchAdapter(matches) {
            context?.toast(it.idEvent)
            startActivity<MatchActivity>(MatchActivity.EXTRA_EVENT to it.idEvent)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchListPresenter(this, request, gson)

        initUI()
    }

    private fun initUI() {
        search.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    presenter.searchMatch(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        rv_search.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = matchAdapter
        }
    }

    override fun showLoading() {
        progressBarSearch.visible()
    }

    override fun hideLoading() {
        progressBarSearch.invisible()
        toast(R.string.no_connection)
    }

    override fun showMatchList(data: List<Match>) {
        matches.clear()
        matches.addAll(data)
        matchAdapter.notifyDataSetChanged()
    }

}