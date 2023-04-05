package com.test.testceibalistusers.ui.screens.post.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.test.testceibalistusers.data.repository.Status
import com.test.testceibalistusers.databinding.ActivityListPostBinding
import com.test.testceibalistusers.ui.screens.post.viewmodel.ListPostViewModel
import com.test.testceibalistusers.utils.MessageManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListPostBinding

    private val viewModel: ListPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        val typeDocuments = it.data?.response

                    }
                    Status.FAILED -> {
                        MessageManager.progressGone(this@ListPostActivity)
                    }
                    else -> {}
                }
            }

        }

        viewModel.getPosts()
    }
}