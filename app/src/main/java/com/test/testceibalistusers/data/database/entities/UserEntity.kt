package com.test.testceibalistusers.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.testceibalistusers.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int= 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String
)

fun User.toDatabase() = UserEntity(id = id, name= name, username =username, email = email,phone = phone)

