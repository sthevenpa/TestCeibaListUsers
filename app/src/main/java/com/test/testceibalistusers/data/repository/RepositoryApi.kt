package com.test.testceibalistusers.data.repository

import com.test.testceibalistusers.data.api.ApiService
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.model.ModelUsers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface RepositoryApi {
    suspend fun getUsers():  Flow<StateApp<ModelUsers>>
    suspend fun getPosts():  Flow<StateApp<ModelPosts>>
}

class RepositoryApiImpl @Inject constructor(
    private val apiService: ApiService,
) : RepositoryApi {
    override suspend fun getUsers(
    ): Flow<StateApp<ModelUsers>> {
        return flow {
            try {

                emit(StateApp.loading("Consultando información"))

                val response =
                    apiService.getUsers()
                val isSuccessful = true

                if (isSuccessful) {

                    val model = ModelUsers(status = true, message = "ok", response = response)

                    val status = model.status ?: false

                    if (status) {

                        emit(StateApp.success(model))
                    } else {
                        val message = model.message ?: "code:1"
                        emit(StateApp.failed(message))
                    }

                } else {
                    val message = "code:2"
                    emit(StateApp.failed(message))
                }
            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: "code:3"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPosts(
    ): Flow<StateApp<ModelPosts>> {
        return flow {
            try {
                emit(StateApp.loading("Consultando información"))

                val response =
                    apiService.getPosts()
                val isSuccessful = true

                if (isSuccessful) {

                    val model =  ModelPosts(status = true, message = "ok", response = response)

                    val status = model.status ?: false

                    if (status) {

                        emit(StateApp.success(model))
                    } else {
                        val message = model.message ?: "code:1"
                        emit(StateApp.failed(message))
                    }

                } else {
                    val message = "code:2"
                    emit(StateApp.failed(message))
                }
            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: "code:3"))
            }
        }.flowOn(Dispatchers.IO)
    }
}