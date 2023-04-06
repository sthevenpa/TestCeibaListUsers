package com.test.testceibalistusers.ui.screens.post.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.repository.*
import com.test.testceibalistusers.domain.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPostViewModel  @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {
    val stateAllPosts = MutableStateFlow(StateApp(Status.INIT, ModelPosts(), ""))
    val stateUserPosts = MutableStateFlow(StateApp(Status.INIT, ModelPosts(), ""))

    fun getPostsByUsers(userId:Int) {
        viewModelScope.launch {
            getPostsUseCase.getPostsByUser(userId).collect {
                stateUserPosts.value = it
            }
        }
    }
}