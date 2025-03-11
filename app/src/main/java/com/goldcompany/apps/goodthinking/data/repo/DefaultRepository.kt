package com.goldcompany.apps.goodthinking.data.repo

import com.goldcompany.apps.goodthinking.data.db.GoodWord
import com.goldcompany.apps.goodthinking.data.db.GoodWordDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultRepository @Inject constructor(
    private val goodWordDao: GoodWordDao
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
}