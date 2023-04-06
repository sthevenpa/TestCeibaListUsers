package com.test.testceibalistusers.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.testceibalistusers.data.database.entities.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM post_table WHERE userId = :userId ORDER BY id ASC")
    suspend fun getPostsByUser(userId:Int):List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts:List<PostEntity>)
}