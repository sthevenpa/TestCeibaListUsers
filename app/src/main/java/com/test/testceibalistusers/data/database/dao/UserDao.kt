package com.test.testceibalistusers.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.testceibalistusers.data.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    suspend fun getAllUsers():List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users:List<UserEntity>)
}