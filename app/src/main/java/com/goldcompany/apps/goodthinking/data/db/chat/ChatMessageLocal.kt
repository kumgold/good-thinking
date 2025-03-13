package com.goldcompany.apps.goodthinking.data.db.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.goldcompany.apps.goodthinking.feature.chat.ChatMessage
import com.goldcompany.apps.goodthinking.feature.chat.Participant
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import java.util.UUID

@Entity(tableName = "chat_messages")
data class ChatMessageLocal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo val text: String = "",
    @ColumnInfo val participant: Participant = Participant.USER,
)

fun ChatMessageLocal.toDefault(): ChatMessage {
    return ChatMessage(
        id, text, participant, false
    )
}

fun ChatMessageLocal.toContent(): Content {
    val participant = if (participant == Participant.USER) "user" else "model"

    return content(role = participant ) { text(text) }
}