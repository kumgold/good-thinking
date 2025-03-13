package com.goldcompany.apps.goodthinking.data.db.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goldcompany.apps.goodthinking.feature.chat.ChatMessage
import com.goldcompany.apps.goodthinking.feature.chat.Participant
import java.util.UUID

@Entity(tableName = "chat_messages")
data class ChatMessageLocal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo val text: String = "",
    @ColumnInfo val participant: Participant = Participant.USER,
)

fun ChatMessageLocal.toChatMessage(): ChatMessage {
    return ChatMessage(
        id, text, participant, false
    )
}