package com.goldcompany.apps.goodthinking.data.db.chat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages")
    suspend fun getAllMessages(): List<ChatMessageLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageLocal)

    @Query("DELETE FROM good_words WHERE id = :ids")
    suspend fun deleteMessage(ids: List<String>)
}