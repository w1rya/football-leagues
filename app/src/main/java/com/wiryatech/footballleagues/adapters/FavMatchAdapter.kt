package com.wiryatech.footballleagues.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.db.FavoriteMatch
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_fav_match.view.*

class FavMatchAdapter(
    private val favoriteMatches: List<FavoriteMatch>,
    private val listener: (FavoriteMatch) -> Unit
) : RecyclerView.Adapter<FavMatchAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindFav(favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {
            itemView.tv_league.text = favoriteMatch.leagueName
            itemView.tv_event.text = favoriteMatch.eventName
            itemView.tv_date.text = favoriteMatch.eventDate

            itemView.setOnClickListener { listener(favoriteMatch) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fav_match, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFav(favoriteMatches[position], listener)
    }

    override fun getItemCount(): Int = favoriteMatches.size
}