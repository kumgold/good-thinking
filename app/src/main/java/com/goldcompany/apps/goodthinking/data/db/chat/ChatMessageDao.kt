package com.goldcompany.apps.goodthinking.data.db.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWord
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages WHERE id = :id")
    suspend fun getChatMessage(id: String): ChatMessageLocal?

    @Query("SELECT * FROM chat_messages")
    suspend fun getAllMessages(): List<ChatMessageLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageLocal)

    @Query("DELETE FROM chat_messages WHERE id IN (:ids)")
    suspend fun deleteMessages(ids: List<String>)
}