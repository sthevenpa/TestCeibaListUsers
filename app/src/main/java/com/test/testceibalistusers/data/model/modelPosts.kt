package com.test.testceibalistusers.data.model

data class ModelPosts(
    val status: Boolean? = null,
    val code: String? = null,
    val message: String? = null,
    val response: ArrayList<ResponsePost> = ArrayList()
)
data class ResponsePost(
    val id: Int? = null,
    val userId: Int? = null,
    val title: String? = null,
    val body: String? = null

    ) {
    override fun toString(): String {
        return "$title"
    }
}
