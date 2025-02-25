package com.goldcompany.apps.goodthinking.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodThinkingDao {
    @Query("SELECT * FROM good_thinking")
    fun getAllThinking(): Flow<List<GoodThinking>>

    @Query("SELECT * FROM good_thinking ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomThinking(): GoodThinking

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertGoodThinking(thinking: GoodThinking)

    @Query("UPDATE good_thinking SET thinking = :thinking WHERE id = :id")
    suspend fun updateGoodThinking(id: Long, thinking: String)

    @Delete
    suspend fun deleteGoodThinking(thinking: GoodThinking)
}