package com.goldcompany.apps.goodthinking.data.repo

import com.goldcompany.apps.goodthinking.data.db.chat.ChatMessageDao
import com.goldcompany.apps.goodthinking.data.db.chat.ChatMessageLocal
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWord
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRepository @Inject constructor(
    private val goodWordDao: GoodWordDao,
    private val chatMessageDao: ChatMessageDao
) {
    fun getAllGoodWord(): Flow<List<GoodWord>> {
        return goodWordDao.getAllGoodWord()
    }

    fun getRandomGoodWord(): Flow<GoodWord> {
        return goodWordDao.getRandomGoodWord()
    }

    suspend fun insertGoodWord(thinking: String) {
        goodWordDao.insertGoodWord(
            GoodWord(word = thinking)
        )
    }

    suspend fun updateGoodWord(id: Long, thinking: String) {
        goodWordDao.updateGoodWord(id, thinking)
    }

    suspend fun deleteGoodWord(id: Long) {
        goodWordDao.deleteGoodWord(id)
    }

    suspend fun getChatMessage(id: String): ChatMessageLocal? {
        return chatMessageDao.getChatMessage(id)
    }

    suspend fun getAllMessages(): List<ChatMessageLocal> {
        return chatMessageDao.getAllMessages()
    }

    suspend fun insertMessage(message: ChatMessageLocal) {
        chatMessageDao.insertMessage(message = message)
    }

    suspend fun deleteMessages(ids: List<String>) {
        try {
            chatMessageDao.deleteMessages(ids)
        } catch (e: Exception) {
            println(e)
        }
    }
}