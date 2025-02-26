package com.goldcompany.apps.goodthinking.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Optional

@Dao
interface GoodThinkingDao {
    @Query("SELECT * FROM good_thinking")
    fun getAllThinking(): Flow<List<GoodThinking>>

    @Query("SELECT * FROM good_thinking ORDER BY RANDOM() LIMIT 1")
    fun getRandomThinking(): Flow<GoodThinking>

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertGoodThinking(thinking: GoodThinking)

    @Query("UPDATE good_thinking SET thinking = :thinking WHERE id = :id")
    suspend fun updateGoodThinking(id: Long, thinking: String)

    @Query("DELETE FROM good_thinking WHERE id = :id")
    suspend fun deleteGoodThinking(id: Long)
}