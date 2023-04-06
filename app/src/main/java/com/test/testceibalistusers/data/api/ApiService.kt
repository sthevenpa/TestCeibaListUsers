package com.test.testceibalistusers.data.api

import com.test.testceibalistusers.data.model.ResponsePost
import com.test.testceibalistusers.data.model.ResponseUser

import retrofit2.http.*

interface ApiService {

    @GET("/users")
    suspend fun getUsers(): ArrayList<ResponseUser>

    @GET("/posts")
    suspend fun getPosts(): ArrayList<ResponsePost>

    @GET("/posts")
    suspend fun getPostsByUser(@Query("userId") userId: Int): ArrayList<ResponsePost>

}