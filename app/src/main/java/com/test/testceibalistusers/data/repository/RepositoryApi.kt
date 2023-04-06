package com.test.testceibalistusers.data.repository

import com.test.testceibalistusers.data.api.ApiService
import com.test.testceibalistusers.data.database.dao.PostDao
import com.test.testceibalistusers.data.database.dao.UserDao
import com.test.testceibalistusers.data.database.entities.PostEntity
import com.test.testceibalistusers.data.database.entities.UserEntity
import com.test.testceibalistusers.data.database.entities.toDatabase
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.model.ModelUsers
import com.test.testceibalistusers.domain.model.toDomain
import com.test.testceibalistusers.utils.AppConstant

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface RepositoryApi {
    suspend fun getUsersFromApi():  Flow<StateApp<ModelUsers>>
    suspend fun getUsersFromDB():  Flow<StateApp<ModelUsers>>
    suspend fun getPostsFromApi():  Flow<StateApp<ModelPosts>>
    suspend fun getPostsByUserApi(userId: Int):  Flow<StateApp<ModelPosts>>
    suspend fun getPostsByUserDB(userId: Int):  Flow<StateApp<ModelPosts>>
    suspend fun insertUsers(users: List<UserEntity>)
    suspend fun insertPosts(posts: List<PostEntity>)
}

class RepositoryApiImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val postDao: PostDao
) : RepositoryApi {
    override suspend fun getUsersFromApi(
    ): Flow<StateApp<ModelUsers>> {
        return flow {
            try {
                emit(StateApp.loading("Consultando usuarios"))

                val response = apiService.getUsers()
                val isSuccessful = response.isNotEmpty()

                if (isSuccessful) {
                    val list = response.map { it.toDomain() }
                    val model = ModelUsers(status = true, message = AppConstant.LIST_FOUND, response = list)

                    emit(StateApp.success(model))
                } else {
                    val message = AppConstant.LIST_NOT_FOUND_API
                    emit(StateApp.failed(message,1))
                }
            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: AppConstant.LIST_NOT_FOUND_EXCEPTION,2))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUsersFromDB(
    ): Flow<StateApp<ModelUsers>> {
        return flow {
            try {
                emit(StateApp.loading("Consultando usuarios"))

                val response :
                        List<UserEntity> = userDao.getAllUsers()
                val isSuccessful = response.isNotEmpty()
                if (isSuccessful) {
                    val list = response.map { it.toDomain() }
                    val model = ModelUsers(status = true, message = AppConstant.LIST_FOUND, response = list)

                    emit(StateApp.success(model))
                }else{
                    val message = AppConstant.LIST_NOT_FOUND_DB
                    emit(StateApp.failed(message,1))
                }

            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: AppConstant.LIST_NOT_FOUND_EXCEPTION,2))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPostsFromApi(
    ): Flow<StateApp<ModelPosts>> {
        return flow {
            try {
                val response = apiService.getPosts()
                val isSuccessful = response.isNotEmpty()

                if (isSuccessful) {
                    val list = response.map { it.toDomain() }
                    val model = ModelPosts(status = true, message = AppConstant.LIST_FOUND, response = list)

                    emit(StateApp.success(model))
                } else {
                    val message = AppConstant.LIST_NOT_FOUND_API
                    emit(StateApp.failed(message,1))
                }
            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: AppConstant.LIST_NOT_FOUND_EXCEPTION,2))
            }
        }.flowOn(Dispatchers.IO)
    }
    override suspend fun getPostsByUserApi(userId: Int
    ): Flow<StateApp<ModelPosts>> {
        return flow {
            try {
                emit(StateApp.loading("Consultando publicaciones"))

                val response =
                    apiService.getPostsByUser(userId)
                val isSuccessful = true

                if (isSuccessful) {
                    val list = response.map { it.toDomain() }
                    val model =  ModelPosts(status = true, message = AppConstant.LIST_FOUND, response = list)

                    emit(StateApp.success(model))

                } else {
                    val message = AppConstant.LIST_NOT_FOUND_API
                    emit(StateApp.failed(message,1))
                }
            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: AppConstant.LIST_NOT_FOUND_EXCEPTION,2))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPostsByUserDB(userId: Int
    ): Flow<StateApp<ModelPosts>> {
        return flow {
            try {
                emit(StateApp.loading("Consultando publicaciones"))

                val response =
                    postDao.getPostsByUser(userId)
                val isSuccessful = true

                if (isSuccessful) {
                    val list = response.map { it.toDomain() }
                    val model =  ModelPosts(status = true, message = AppConstant.LIST_FOUND, response = list)

                    emit(StateApp.success(model))

                } else {
                    val message = AppConstant.LIST_NOT_FOUND_API
                    emit(StateApp.failed(message,1))
                }
            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: AppConstant.LIST_NOT_FOUND_EXCEPTION,2))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertAll(users)
    }

    override suspend fun insertPosts(posts: List<PostEntity>) {
        postDao.insertAll(posts)
    }

}