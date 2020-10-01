package com.wiryatech.footballleagues.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.models.Team
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team.view.*

class TeamsAdapter(
    private val teams: MutableList<Team>,
    private val listener: (Team) -> Unit
) : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindTeam(team: Team, listener: (Team) -> Unit) {
            itemView.iv_team.load(team.strTeamBadge) {
                crossfade(true)
            }
            itemView.tv_team_name.text = team.strTeam
            itemView.tv_team_desc.text = team.strDescriptionEN

            itemView.setOnClickListener { listener(team) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTeam(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size
}