package com.wiryatech.footballleagues.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.db.Favorite
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_fav_match.view.*

class FavoriteAdapter(
    private val favorites: List<Favorite>,
    private val listener: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindFav(favorite: Favorite, listener: (Favorite) -> Unit) {
            itemView.tv_league.text = favorite.leagueName
            itemView.tv_event.text = favorite.eventName
            itemView.tv_date.text = favorite.eventDate

            itemView.setOnClickListener { listener(favorite) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fav_match, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFav(favorites[position], listener)
    }

    override fun getItemCount(): Int = favorites.size
}