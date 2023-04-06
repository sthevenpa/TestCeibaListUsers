package com.test.testceibalistusers.data.model

import com.test.testceibalistusers.domain.model.Post

data class ModelPosts(
    val status: Boolean? = null,
    val code: String? = null,
    val message: String? = null,
    val response: List<Post> = ArrayList()
)
data class ResponsePost(
    val id: Int = 0,
    val userId: Int = 0,
    val title: String = "",
    val body: String = ""

    ) {
    override fun toString(): String {
        return "$id $userId $title"
    }
}
