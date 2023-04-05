package com.test.testceibalistusers.ui.screens.post.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.testceibalistusers.data.model.ResponsePost
import com.test.testceibalistusers.data.repository.Status
import com.test.testceibalistusers.databinding.ActivityListPostBinding
import com.test.testceibalistusers.ui.screens.post.adapter.SelectPostAdapter
import com.test.testceibalistusers.ui.screens.post.viewmodel.ListPostViewModel
import com.test.testceibalistusers.utils.AdapterControl
import com.test.testceibalistusers.utils.MessageManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPostBinding

    private val viewModel: ListPostViewModel by viewModels()

    private var postList: ArrayList<ResponsePost> = ArrayList()
    private var adapter: SelectPostAdapter? = null

    private var id: Int = 0
    private var name: String = ""
    private var email: String = ""
    private var phone: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        id = intent.extras!!.getInt("id", 0)
        name = intent.extras!!.getString("name", "")
        email = intent.extras!!.getString("email", "")
        phone = intent.extras!!.getString("phone", "")
        loadControls()
    }
    private fun loadControls() {


        lifecycleScope.launch {
            viewModel.statePost.collect {
                when (it.status) {
                    Status.LOADING -> {
                        MessageManager.progressVisible(this@ListPostActivity)
                    }
                    Status.SUCCESS -> {
                        MessageManager.progressGone(this@ListPostActivity)
                        val list = it.data?.response
                        postList = list!!

                        adapter = SelectPostAdapter(postList)
                        binding.rvPosts.adapter = adapter
                    }
                    Status.FAILED -> {
                        MessageManager.progressGone(this@ListPostActivity)
                        val code = it.code
                        println("Error {$code}")
                    }
                    else -> {}
                }
            }

        }
        binding.tvUserName.text = name
        binding.tvEmail.text = email
        binding.tvPhone.text = phone

        viewModel.getPostsByUsers(id)

        binding.rvPosts.setHasFixedSize(true)
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        binding.rvPosts.adapter = AdapterControl()

        binding.toolbar.apply {
            setNavigationOnClickListener {
                finish()
            }
        }
    }
}