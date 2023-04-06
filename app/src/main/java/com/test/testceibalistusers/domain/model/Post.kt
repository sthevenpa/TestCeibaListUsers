package com.test.testceibalistusers.domain.model

import com.test.testceibalistusers.data.database.entities.PostEntity
import com.test.testceibalistusers.data.model.ResponsePost

data class Post(val id: Int ,
                val userId: Int,
                val title: String ,
                val body: String )

fun PostEntity.toDomain() = Post(id,userId,title,body)

fun ResponsePost.toDomain() = Post(id,userId,title,body)
