package ru.b1nd.statistics.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.country_info_item.view.*
import ru.b1nd.statistics.R

class StatisticsCountriesInfoAdapter :
    RecyclerView.Adapter<StatisticsCountriesInfoAdapter.StatisticsCountriesInfoHolder>() {

    var items: List<StatisticsCountriesInfoData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatisticsCountriesInfoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_info_item, parent, false)

        return StatisticsCountriesInfoHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: StatisticsCountriesInfoHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class StatisticsCountriesInfoHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: StatisticsCountriesInfoData) {
            view.country_info_item_country.text = data.country
            view.country_info_item_confirmed.text = data.confirmed
            view.country_info_item_deaths.text = data.deaths
            view.country_info_item_recovered.text = data.recovered
        }
    }
}