package com.goldcompany.apps.goodthinking.data.repo

import com.goldcompany.apps.goodthinking.data.db.GoodThinking
import com.goldcompany.apps.goodthinking.data.db.GoodThinkingDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRepository @Inject constructor(
    private val goodThinkingDao: GoodThinkingDao
) {
    fun getAllThinking(): Flow<List<GoodThinking>> {
        return goodThinkingDao.getAllThinking()
    }

    fun getThinking(): Flow<GoodThinking> {
        return goodThinkingDao.getRandomThinking()
    }

    suspend fun insertGoodThinking(thinking: String) {
        goodThinkingDao.insertGoodThinking(
            GoodThinking(thinking = thinking)
        )
    }

    suspend fun updateGoodThinking(id: Long, thinking: String) {
        goodThinkingDao.updateGoodThinking(id, thinking)
    }

    suspend fun deleteGoodThinking(id: Long) {
        goodThinkingDao.deleteGoodThinking(id)
    }
}