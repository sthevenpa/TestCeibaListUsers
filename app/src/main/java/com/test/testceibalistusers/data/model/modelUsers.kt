package com.test.testceibalistusers.data.model

data class ModelUsers(
    val status: Boolean? = null,
    val code: String? = null,
    val message: String? = null,
    val response: ArrayList<ResponseUser> = ArrayList()
)
data class ResponseUser(
    val id: Int? = null,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
    val phone: String? = null,

) {
    override fun toString(): String {
        return "$name"
    }
}
