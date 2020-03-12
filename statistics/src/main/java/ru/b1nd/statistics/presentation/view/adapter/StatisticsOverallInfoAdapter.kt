package ru.b1nd.statistics.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.overall_info_item.view.*
import ru.b1nd.statistics.R

class StatisticsOverallInfoAdapter : RecyclerView.Adapter<StatisticsOverallInfoAdapter.StatisticsOverallInfoViewHolder>() {

    var items: List<StatisticsOverallInfoData> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatisticsOverallInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.overall_info_item, parent, false)

        return StatisticsOverallInfoViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: StatisticsOverallInfoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    class StatisticsOverallInfoViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: StatisticsOverallInfoData) {
            view.overall_info_item_label.text = data.label
            view.overall_info_item_value.text = data.value
        }
    }
}