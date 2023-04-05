package com.test.testceibalistusers.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterControl : RecyclerView.Adapter<AdapterControl.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {}
    override fun getItemCount(): Int {
        return 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}