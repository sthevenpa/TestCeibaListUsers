package com.test.testceibalistusers.ui.screens.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testceibalistusers.data.model.ResponseUser
import com.test.testceibalistusers.databinding.CardUserBinding

class SelectUserAdapter(private val userList: ArrayList<ResponseUser>,
                        private val onUserUpdate:(Int) -> Unit): RecyclerView.Adapter<SelectUserAdapter.ViewHolder>() {

    private var selected: Int = 0
    class ViewHolder(val binding: CardUserBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = userList[position]
        holder.binding.tvName.text = currentPosition.name
        holder.binding.tvPhone.text = currentPosition.phone
        holder.binding.tvEmail.text = currentPosition.email

        holder.binding.root.setOnClickListener{onUpdate(position)}
        holder.binding.btSeePost.setOnClickListener {onUpdate(position)}

    }

    private fun onUpdate(position: Int) {
        selected = userList[position].id!!
        onUserUpdate.invoke(selected)
    }

    override fun getItemCount() = userList.size

    override fun getItemViewType(position: Int) = position
}