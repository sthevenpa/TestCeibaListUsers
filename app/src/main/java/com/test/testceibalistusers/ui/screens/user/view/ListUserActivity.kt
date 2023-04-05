package com.test.testceibalistusers.ui.screens.user.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testceibalistusers.data.model.ResponseUser
import com.test.testceibalistusers.data.repository.Status
import com.test.testceibalistusers.databinding.ActivityListUserBinding
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

    private var usersList: ArrayList<ResponseUser> = ArrayList()
    private var adapter: SelectUserAdapter? = null
    private var selectUserId:Int = 0

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
                        usersList = list!!

                        adapter = SelectUserAdapter(usersList) { userId ->
                            selectUserId = userId
                        }
                        binding.rvUsers.adapter = adapter
                    }
                    Status.FAILED -> {
                        MessageManager.progressGone(this@ListUserActivity)
                        val code = it.code

                    }
                    else -> {}
                }
            }
        }

        viewModel.getUsers()

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = AdapterControl()

    }
}