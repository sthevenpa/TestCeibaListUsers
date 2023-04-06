package com.test.testceibalistusers.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.testceibalistusers.data.database.dao.PostDao
import com.test.testceibalistusers.data.database.dao.UserDao
import com.test.testceibalistusers.data.database.entities.PostEntity
import com.test.testceibalistusers.data.database.entities.UserEntity

@Database(entities = [UserEntity::class,PostEntity::class], version = 2,exportSchema = false)
abstract class TestCeibaDatabase: RoomDatabase() {
    abstract fun getUserDao():UserDao

    abstract fun getPostDao(): PostDao
}