package com.test.testceibalistusers.domain.model

import com.test.testceibalistusers.data.database.entities.UserEntity
import com.test.testceibalistusers.data.model.ResponseUser

data class User(val id: Int ,
                val name: String,
                val username: String ,
                val email: String ,
                val phone: String )

fun UserEntity.toDomain() = User(id,name,username,email,phone)

fun ResponseUser.toDomain() = User(id,name,username,email,phone)
