package com.test.testceibalistusers.data.repository

import com.test.testceibalistusers.data.api.ApiService
import com.test.testceibalistusers.data.database.dao.UserDao
import com.test.testceibalistusers.data.database.entities.UserEntity
import com.test.testceibalistusers.data.database.entities.toDatabase
import com.test.testceibalistusers.data.model.ModelPosts
import com.test.testceibalistusers.data.model.ModelUsers
import com.test.testceibalistusers.domain.model.toDomain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


interface RepositoryApi {
    suspend fun getUsers():  Flow<StateApp<ModelUsers>>
    suspend fun getPostsByUser(userId: Int):  Flow<StateApp<ModelPosts>>
    suspend fun insertUsers(users: List<UserEntity>)
}

class RepositoryApiImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : RepositoryApi {
    override suspend fun getUsers(
    ): Flow<StateApp<ModelUsers>> {
        return flow {
            try {

                emit(StateApp.loading("Consultando usuarios"))

                val responseDB :
                        List<UserEntity> = userDao.getAllUsers()
                val isSuccessfulDB = responseDB.isEmpty()

                if (isSuccessfulDB) {
                    val responseApi = apiService.getUsers()
                    val isSuccessfulApi = responseApi.isNotEmpty()

                    if (isSuccessfulApi) {
                        val entitiesList = responseApi.map { it.toDomain() }
                        insertUsers(entitiesList.map { it.toDatabase() })
                        val model = ModelUsers(status = true, message = "ok", response = entitiesList)
                        println("model api")
                        emit(StateApp.success(model))
                    } else {
                        val message = "code:2"
                        emit(StateApp.failed(message))
                    }
                }else{
                    val list = responseDB.map{it.toDomain()}
                    val model = ModelUsers(status = true, message = "ok", response = list)
                    println("model db")
                    emit(StateApp.success(model))
                }
            } catch (ex: Exception) {
                emit(StateApp.failed(ex.message ?: "code:3"))
            }
        }.flowOn(Dispatchers.IO)
    }
    override suspend fun getPostsByUser(userId: Int
    ): Flow<StateApp<ModelPosts>> {
        return flow {
            try {
                emit(StateApp.loading("Consultando publicaciones"))

                val response =
                    apiService.getPosts(userId)
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

    override suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertAll(users)
    }

}