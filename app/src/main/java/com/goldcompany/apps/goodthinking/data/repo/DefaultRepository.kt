package com.goldcompany.apps.goodthinking.data.repo

import com.goldcompany.apps.goodthinking.data.db.GoodThinking
import com.goldcompany.apps.goodthinking.data.db.GoodThinkingDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRepository @Inject constructor(
    private val goodThinkingDao: GoodThinkingDao
) {
    suspend fun getAllThinking(): List<GoodThinking> {
        return goodThinkingDao.getAllThinking()
    }

    suspend fun getThinking(): GoodThinking {
        return goodThinkingDao.getRandomThinking()
    }

    suspend fun insertGoodThinking(word: String) {
        goodThinkingDao.insertGoodThinking(
            GoodThinking(thinking = word)
        )
    }
}