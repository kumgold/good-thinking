package com.goldcompany.apps.goodthinking.feature.chat

import com.goldcompany.apps.goodthinking.data.db.chat.ChatMessageLocal
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.content
import java.util.UUID

enum class Participant {
    USER, MODEL, ERROR
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val participant: Participant = Participant.USER,
    var isPending: Boolean = false
)

fun ChatMessage.toContent(): Content {
    val participant = if (participant == Participant.USER) "user" else "model"

    return content(role = participant ) { text(text) }
}

fun ChatMessage.toLocal(): ChatMessageLocal {
    return ChatMessageLocal(
        id = id,
        text = text,
        participant = participant
    )
}