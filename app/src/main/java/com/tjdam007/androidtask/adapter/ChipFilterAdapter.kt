package com.tjdam007.androidtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.tjdam007.androidtask.R
import com.tjdam007.androidtask.utils.*
import kotlinx.android.synthetic.main.item_chip_filter.view.*

class ChipFilterAdapter(val context: Context?) : RecyclerView.Adapter<ChipFilterVH>() {

    var list = arrayListOf<HashMap<String, ArrayList<String>>>()
    var selectedChips = HashSet<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipFilterVH {
        return ChipFilterVH(LayoutInflater.from(context).inflate(R.layout.item_chip_filter, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ChipFilterVH, position: Int) {
        val map = list.get(position)
        holder.itemView.tvFilterHeader.text = map.get(HEADER)?.get(0)
        val dataList = map.get(DATA)
        dataList?.forEach {
            holder.itemView.chipGroup.addView(Chip(context).apply {
                setLayoutDirection(View.LAYOUT_DIRECTION_RTL)
                chipBackgroundColor = getChipBg(context)
                setTextColor(getChipTextColor(context))
                chipStrokeColor = getChipStorkColor(context)
                checkedIcon = ContextCompat.getDrawable(context, R.drawable.ic_check_black_24dp)
                chipIcon = ContextCompat.getDrawable(context, R.drawable.ic_add_black_24dp)
                chipStrokeWidth = resources.getDimension(R.dimen.dp1)
                isCheckable = true
                if (selectedChips.contains(it)) {
                    isChecked=true
                    isCheckedIconVisible = true
                    isChipIconVisible = false
                } else {
                    isChecked=false
                    isCheckedIconVisible = false
                    isChipIconVisible = true
                }
                text = it
                isCheckedIconVisible = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedChips.add(it)
                        isCheckedIconVisible = true
                        isChipIconVisible = false
                    } else {
                        selectedChips.remove(it)
                        isCheckedIconVisible = false
                        isChipIconVisible = true
                    }
                }
            })
        }
    }

    fun setData(data: ArrayList<HashMap<String, ArrayList<String>>>) {
        this.list = data
        notifyDataSetChanged()
    }
}


class ChipFilterVH(view: View) : RecyclerView.ViewHolder(view)
