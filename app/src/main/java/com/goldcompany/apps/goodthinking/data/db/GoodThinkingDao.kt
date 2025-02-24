package com.goldcompany.apps.goodthinking.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GoodThinkingDao {
    @Query("SELECT * FROM good_thinking")
    suspend fun getAllThinking(): List<GoodThinking>

    @Query("SELECT * FROM good_thinking ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomThinking(): GoodThinking

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertGoodThinking(thinking: GoodThinking)

    @Delete
    suspend fun deleteGoodThinking(thinking: GoodThinking)
}