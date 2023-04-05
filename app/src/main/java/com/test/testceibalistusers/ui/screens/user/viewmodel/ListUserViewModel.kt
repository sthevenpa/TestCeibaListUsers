package com.test.testceibalistusers.ui.screens.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.testceibalistusers.data.model.ModelUsers
import com.test.testceibalistusers.data.repository.*
import com.test.testceibalistusers.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListUserViewModel  @Inject constructor(
    private val repositoryApi: RepositoryApi
) : ViewModel() {
    val stateUser = MutableStateFlow(StateApp(Status.INIT, ModelUsers(), ""))

    fun getUsers() {
        viewModelScope.launch {
            repositoryApi.getUsers().collect {
                stateUser.value = it
            }
        }
    }

}