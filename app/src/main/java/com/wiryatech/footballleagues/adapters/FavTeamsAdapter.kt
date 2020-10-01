package com.wiryatech.footballleagues.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.db.FavoriteTeams
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team.view.*

class FavTeamsAdapter(
    private val favoriteTeams: List<FavoriteTeams>,
    private val listener: (FavoriteTeams) -> Unit
) : RecyclerView.Adapter<FavTeamsAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindFav(favoriteTeam: FavoriteTeams, listener: (FavoriteTeams) -> Unit) {
            itemView.tv_team_name.text = favoriteTeam.teamName
            itemView.tv_team_desc.text = favoriteTeam.teamDesc
            favoriteTeam.teamBadge.let { itemView.iv_team.load(it) }

            itemView.setOnClickListener { listener(favoriteTeam) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindFav(favoriteTeams[position], listener)
    }

    override fun getItemCount(): Int = favoriteTeams.size

}