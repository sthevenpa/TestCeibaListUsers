package com.test.testceibalistusers.domain

import com.test.testceibalistusers.data.database.entities.toDatabase
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.repository.RepositoryApi
import com.test.testceibalistusers.data.repository.StateApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: RepositoryApi) {
    suspend fun getPosts(): Flow<StateApp<ModelPosts>> {
        return flow {
                repository.getPostsFromApi().collect {postApi ->
                            val list = postApi.data?.response ?: ArrayList()
                            repository.insertPosts(list.map { it.toDatabase() })
                            emit(postApi)
                }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPostsByUser(userId:Int): Flow<StateApp<ModelPosts>> {
        return flow {
            repository.getPostsByUserApi(userId).collect {modelApi ->
                if (modelApi.code == 1 || modelApi.code == 2) {
                    repository.getPostsByUserDB(userId).collect { modelDB ->
                        emit(modelDB)
                    }
                }else{
                    emit(modelApi)
                }
            }

        }.flowOn(Dispatchers.IO)
    }
}