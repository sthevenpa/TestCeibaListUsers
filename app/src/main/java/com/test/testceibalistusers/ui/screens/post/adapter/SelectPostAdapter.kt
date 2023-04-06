package com.test.testceibalistusers.ui.screens.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testceibalistusers.databinding.CardPostBinding
import com.test.testceibalistusers.domain.model.Post

class SelectPostAdapter(private val postList: ArrayList<Post>): RecyclerView.Adapter<SelectPostAdapter.ViewHolder>() {

    class ViewHolder(val binding: CardPostBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.tvTitle.text = postList[position].title
        holder.binding.tvBody.text = postList[position].body

    }

    override fun getItemCount() = postList.size

    override fun getItemViewType(position: Int) = position
}