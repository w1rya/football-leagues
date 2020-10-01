package com.wiryatech.footballleagues.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiryatech.footballleagues.R
import com.wiryatech.footballleagues.models.Standing
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_standing.view.*

class StandingAdapter(
    private val standing: MutableList<Standing>
) : RecyclerView.Adapter<StandingAdapter.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindStanding(standing: Standing) {
            itemView.tv_position.text = (adapterPosition + 1).toString()
            itemView.tv_team.text = standing.name
            itemView.tv_played.text = standing.played.toString()
            itemView.tv_win.text = standing.win.toString()
            itemView.tv_draw.text = standing.draw.toString()
            itemView.tv_loss.text = standing.loss.toString()
            itemView.tv_goal.text = standing.goalsdifference.toString()
            itemView.tv_point.text = standing.total.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_standing, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindStanding(standing[position])
    }

    override fun getItemCount(): Int = standing.size
}