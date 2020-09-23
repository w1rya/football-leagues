package com.wiryatech.footballleagues.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.models.League
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_league.view.*

class GridLeagueAdapter(
    private val context: Context,
    private val leagues: List<League>,
    private val listener: (League) -> Unit
) : RecyclerView.Adapter<GridLeagueAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindLeague(league: League, listener: (League) -> Unit) {
            itemView.tv_name.text = league.strLeague
            league.badge?.let {
                itemView.iv_badge.load(it) {
                    placeholder(R.drawable.ic_round_sports_soccer_24)
                    size(120)
                }
            }
            itemView.setOnClickListener { listener(league) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_league, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindLeague(leagues[position], listener)
    }

    override fun getItemCount(): Int = leagues.size
}