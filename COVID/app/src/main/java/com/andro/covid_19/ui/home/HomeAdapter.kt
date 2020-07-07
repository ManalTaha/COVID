package com.andro.covid_19.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andro.covid_19.R
import com.andro.retro.json_models.CountriesStat
import kotlinx.android.synthetic.main.item.view.*


class HomeAdapter(val items: List<CountriesStat>) : RecyclerView.Adapter< HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.itemView.tvTitle.text =items[position].country_name
        holder.itemView.tvCases.text = items[position].cases
        holder.itemView.tvDeathCase.text = items[position].deaths
        holder.itemView.tvRecoverCase.text = items[position].total_recovered

    }


}