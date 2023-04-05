package com.test.testceibalistusers.data.model

import com.test.testceibalistusers.domain.model.User

data class ModelUsers(
    val status: Boolean? = null,
    val code: String? = null,
    val message: String? = null,
    val response: List<User> = ArrayList()
)
data class ResponseUser(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",

) {
    override fun toString(): String {
        return "-$id $name $email $phone-"
    }
}
