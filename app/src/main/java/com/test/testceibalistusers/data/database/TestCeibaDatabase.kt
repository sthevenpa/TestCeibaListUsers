package com.test.testceibalistusers.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.testceibalistusers.data.database.dao.UserDao
import com.test.testceibalistusers.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class TestCeibaDatabase: RoomDatabase() {
    abstract fun getUserDao():UserDao
}