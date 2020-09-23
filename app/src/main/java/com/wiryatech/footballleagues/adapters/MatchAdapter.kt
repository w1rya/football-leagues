package com.wiryatech.footballleagues.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.models.Match
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_match.view.*

class MatchAdapter(
    private val matches: MutableList<Match>,
    private val listener: (Match) -> Unit
) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindMatch(match: Match, listener: (Match) -> Unit) {
            // General
            itemView.tv_league.text = match.strLeague
            itemView.tv_season.text = match.strSeason
            itemView.tv_venue.text = match.strVenue
            // Home
            itemView.tv_home_name.text = match.strHomeTeam
            itemView.tv_home_score.text = match.intHomeScore.toString()
            // Away
            itemView.tv_away_name.text = match.strAwayTeam
            itemView.tv_away_score.text = match.intAwayScore.toString()
            // listener
            itemView.setOnClickListener { listener(match) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMatch(matches[position], listener)
    }

    override fun getItemCount(): Int = matches.size
}