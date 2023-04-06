package com.test.testceibalistusers.ui.screens.user.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testceibalistusers.data.repository.Status
import com.test.testceibalistusers.databinding.ActivityListUserBinding
import com.test.testceibalistusers.domain.model.User
import com.test.testceibalistusers.ui.screens.post.view.ListPostActivity
import com.test.testceibalistusers.ui.screens.user.adapter.SelectUserAdapter
import com.test.testceibalistusers.ui.screens.user.viewmodel.ListUserViewModel
import com.test.testceibalistusers.utils.AdapterControl
import com.test.testceibalistusers.utils.MessageManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListUserActivity : AppCompatActivity(){
    private lateinit var binding: ActivityListUserBinding

    private val viewModel: ListUserViewModel by viewModels()

    private var usersList: ArrayList<User> = ArrayList()
    private var adapter: SelectUserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadControls()
    }
    private fun loadControls() {

        lifecycleScope.launch {
            viewModel.stateUser.collect {
                when (it.status) {
                    Status.LOADING -> {
                        MessageManager.progressVisible(this@ListUserActivity)
                    }
                    Status.SUCCESS -> {
                        MessageManager.progressGone(this@ListUserActivity)
                        val list = it.data?.response
                        val userArrayList = list?.let { it1 -> ArrayList<User>(it1) }
                        usersList = userArrayList!!

                        adapter = SelectUserAdapter(usersList) { user ->
                            selectUser(user)
                        }
                        binding.rvUsers.adapter = adapter
                        viewModel.getPosts()
                    }
                    Status.FAILED -> {
                        MessageManager.progressGone(this@ListUserActivity)
                        val code = it.code
                        val message = it.message
                        println("Error {$code} {$message}")
                        validateEmpty(usersList)
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launch {
            viewModel.statePost.collect {
                when (it.status) {
                    Status.LOADING -> {
                        MessageManager.progressVisible(this@ListUserActivity)
                    }
                    Status.SUCCESS -> {
                        MessageManager.progressGone(this@ListUserActivity)
                    }
                    Status.FAILED -> {
                        MessageManager.progressGone(this@ListUserActivity)
                        val code = it.code
                        val message = it.message
                        println("Error {$code} {$message}")
                    }
                    else -> {}
                }
            }
        }

        viewModel.getUsers()

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = AdapterControl()

        binding.tieSearch.addTextChangedListener { name ->
            val listFiltered = usersList.filter { user ->
                user.name.lowercase().contains(name.toString().lowercase()) }
            val userList = ArrayList<User>(listFiltered)
            adapter?.updateUserList(userList)
            validateEmpty(userList)
        }

    }
    private fun validateEmpty(userList: ArrayList<User>){
        if(userList.isEmpty()){
            binding.rvUsers.visibility = View.GONE
            binding.tvMsg.visibility = View.VISIBLE
        }else{
            binding.rvUsers.visibility = View.VISIBLE
            binding.tvMsg.visibility = View.GONE
        }
    }

    private fun selectUser(user:User){
        val intent = Intent(this, ListPostActivity::class.java)
        intent.putExtra("id", user.id)
        intent.putExtra("name", user.name)
        intent.putExtra("email", user.email)
        intent.putExtra("phone", user.phone)
        startActivity(intent)
    }
}