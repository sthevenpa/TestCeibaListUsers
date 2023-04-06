package com.test.testceibalistusers.ui.screens.user.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.model.ModelUsers
import com.test.testceibalistusers.data.repository.StateApp
import com.test.testceibalistusers.data.repository.Status
import com.test.testceibalistusers.domain.GetPostsUseCase
import com.test.testceibalistusers.domain.GetUsersUseCase
import com.test.testceibalistusers.domain.model.Post
import com.test.testceibalistusers.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListUserViewModelTest{

    @RelaxedMockK
    private lateinit var getPostsUseCase: GetPostsUseCase

    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    private lateinit var listUserViewModel: ListUserViewModel

    @get:Rule
    var rule:InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        listUserViewModel = ListUserViewModel(getUsersUseCase,getPostsUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `get all users from api`()= runTest{
        //Given
        val list = listOf(User(1,"Marco","mayu@gmail.com","314-453-2524"),User(1,"Luis","mayu@gmail.com","314-453-2524"))
        val user = ModelUsers(true,"0","ok",list)
        val stateUsers = MutableStateFlow(StateApp(Status.INIT, user, ""))

        coEvery { getUsersUseCase.getUsers() } returns stateUsers

        //When
        listUserViewModel.getUsers()


        val result = getUsersUseCase.getUsers()

        //Then
        assert(stateUsers == result)
    }

    @Test
    fun `get all post from api`()= runTest {
        //Given
        val list = listOf(Post(1,1,"Hola","prueba uno dos"),Post(2,1,"Porque","Prueba que no funca"))
        val post = ModelPosts(true,"0","ok",list)
        val statePosts = MutableStateFlow(StateApp(Status.INIT, post, ""))

        coEvery { getPostsUseCase.getPosts() } returns statePosts

        //When
        listUserViewModel.getPosts()


        val result = getPostsUseCase.getPosts()

        //Then
        assert(statePosts == result)
    }


}