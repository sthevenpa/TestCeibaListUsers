package com.test.testceibalistusers.ui.screens.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.testceibalistusers.data.model.ResponseUser
import com.test.testceibalistusers.databinding.CardUserBinding
import com.test.testceibalistusers.domain.model.User

class SelectUserAdapter(private val userList: ArrayList<User>,
                        private val onUserUpdate:(User) -> Unit): RecyclerView.Adapter<SelectUserAdapter.ViewHolder>() {

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
        onUserUpdate.invoke(userList[position])
    }

    override fun getItemCount() = userList.size

    override fun getItemViewType(position: Int) = position
}