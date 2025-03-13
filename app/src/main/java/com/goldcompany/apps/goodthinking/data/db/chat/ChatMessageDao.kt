package com.goldcompany.apps.goodthinking.data.db.chat

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages")
    fun getAllMessages(): Flow<List<ChatMessageLocal>>
}