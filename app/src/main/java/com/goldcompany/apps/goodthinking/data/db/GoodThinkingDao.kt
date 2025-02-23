package com.goldcompany.apps.goodthinking.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface GoodThinkingDao {
    @Query("select * from good_thinking")
    fun getAllThinking(): List<GoodThinking>
}