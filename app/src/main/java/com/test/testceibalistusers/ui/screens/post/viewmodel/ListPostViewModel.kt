package com.test.testceibalistusers.ui.screens.post.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPostViewModel  @Inject constructor(
    private val repositoryApi: RepositoryApi
) : ViewModel() {
    val statePost = MutableStateFlow(StateApp(Status.INIT, ModelPosts(), ""))

    fun getPosts() {
        viewModelScope.launch {
            repositoryApi.getPosts().collect {
                statePost.value = it
            }
        }
    }
}