package com.tjdam007.androidtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tjdam007.androidtask.Callback
import com.tjdam007.androidtask.R
import com.tjdam007.androidtask.model.Job
import com.tjdam007.androidtask.utils.getDays
import kotlinx.android.synthetic.main.item_job.view.*

class JobAdapter(val context: Context) : RecyclerView.Adapter<JobVH>() {
    var list = arrayListOf<Job>()
    var callback: Callback<Job>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobVH {
        return JobVH(
            LayoutInflater.from(context)
                .inflate(R.layout.item_job, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: JobVH, position: Int) {
        val job = list[position]
        holder.itemView.tvJobTitle.text = job.title
        holder.itemView.tvCompanyAddress.text = job.location?.trim()
        holder.itemView.tvCompanyName.text = job.company
        holder.itemView.tvPostedOn.text = context.getString(R.string.posted_on, getDays(job.created_at!!))
        holder.itemView.tvKnowMore.setOnClickListener {
            callback?.onCallback(job)
        }
    }
}

//ViewHolder for adapter
class JobVH(view: View) : RecyclerView.ViewHolder(view)