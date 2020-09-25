package com.wiryatech.footballleagues.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.adapters.GridLeagueAdapter
import com.wiryatech.footballleagues.models.League
import com.wiryatech.footballleagues.ui.activities.DetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity

class HomeFragment : Fragment() {

    private var leagues: MutableList<League> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initUI()
    }

    private fun initData() {
        val id = resources.getIntArray(R.array.id)
        val name = resources.getStringArray(R.array.name)
        val badge = resources.obtainTypedArray(R.array.badge)

        leagues.clear()

        for (i in id.indices) {
            leagues.add(
                League(
                    id[i],
                    name[i],
                    badge.getResourceId(i, 0),
                )
            )
        }

        badge.recycle()
    }

    private fun initUI() {
        rv_league.layoutManager = GridLayoutManager(context, 2)
        rv_league.adapter = context?.let {
            GridLeagueAdapter(it, leagues) {item ->
                val league = League(item.idLeague, item.strLeague, item.badge)
                startActivity<DetailActivity>(DetailActivity.EXTRA_LEAGUE to league)
            }
        }
    }
}