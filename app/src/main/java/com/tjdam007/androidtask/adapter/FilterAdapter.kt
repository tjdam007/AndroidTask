package com.tjdam007.androidtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tjdam007.androidtask.Callback
import com.tjdam007.androidtask.R
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(val context: Context) : RecyclerView.Adapter<FilterVH>() {
    var list = arrayListOf<String>()
    var callback: Callback<String>? = null
    var isChecked = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterVH {
        return FilterVH(LayoutInflater.from(context).inflate(R.layout.item_filter, parent, false))

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: FilterVH, position: Int) {
        holder.itemView.tvFilter.text = list[position]
        holder.itemView.setOnClickListener {
            callback?.onCallback(list[position])
        }
        if (isChecked) {
            holder.itemView.tvFilter.isChipIconVisible = true
            holder.itemView.tvFilter.chipStrokeWidth = 0f
            holder.itemView.tvFilter.setTextColor(ContextCompat.getColor(context, R.color.mediumPurple))
            holder.itemView.tvFilter.setChipBackgroundColorResource(R.color.chip_selected)
        } else {
            holder.itemView.tvFilter.isChipIconVisible = false
            holder.itemView.tvFilter.chipStrokeWidth = context.resources.getDimension(R.dimen.dp1)
            holder.itemView.tvFilter.setTextColor(ContextCompat.getColor(context, R.color.darkSlateGray))
            holder.itemView.tvFilter.setChipBackgroundColorResource(R.color.white)
        }
    }
}

class FilterVH(view: View) : RecyclerView.ViewHolder(view)
