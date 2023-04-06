package com.test.testceibalistusers.ui.screens.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.model.ModelUsers
import com.test.testceibalistusers.data.repository.*
import com.test.testceibalistusers.domain.GetPostsUseCase
import com.test.testceibalistusers.domain.GetUsersUseCase
import com.test.testceibalistusers.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListUserViewModel  @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {
    val stateUser = MutableStateFlow(StateApp(Status.INIT, ModelUsers(), ""))
    val statePost = MutableStateFlow(StateApp(Status.INIT, ModelPosts(), ""))

    fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase.getUsers().collect {
                stateUser.value = it
            }
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase.getPosts().collect {
                statePost.value = it
            }
        }
    }

}