package com.test.testceibalistusers.domain

import com.test.testceibalistusers.data.database.entities.toDatabase
import com.test.testceibalistusers.data.model.ModelUsers
import com.test.testceibalistusers.data.repository.RepositoryApi
import com.test.testceibalistusers.data.repository.StateApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: RepositoryApi) {
    suspend fun getUsers(): Flow<StateApp<ModelUsers>> {
        return flow {
                repository.getUsersFromDB().collect {modelDB ->
                    if (modelDB.code == 1 || modelDB.code == 2) {
                        repository.getUsersFromApi().collect { usersApi ->
                            val list = usersApi.data?.response ?: ArrayList()
                            repository.insertUsers(list.map { it.toDatabase() })
                            emit(usersApi)
                        }
                    }else{
                        emit(modelDB)
                    }
                }
        }.flowOn(Dispatchers.IO)
    }
}