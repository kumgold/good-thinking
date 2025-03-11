package com.goldcompany.apps.goodthinking.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodWordDao {
    @Query("SELECT * FROM good_words")
    fun getAllGoodWord(): Flow<List<GoodWord>>

    @Query("SELECT * FROM good_words ORDER BY RANDOM() LIMIT 1")
    fun getRandomGoodWord(): Flow<GoodWord>

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertGoodWord(word: GoodWord)

    @Query("UPDATE good_words SET word = :word WHERE id = :id")
    suspend fun updateGoodWord(id: Long, word: String)

    @Query("DELETE FROM good_words WHERE id = :id")
    suspend fun deleteGoodWord(id: Long)
}