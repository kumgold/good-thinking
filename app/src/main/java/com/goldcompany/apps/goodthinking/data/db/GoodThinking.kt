package com.goldcompany.apps.goodthinking.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "good_thinking")
class GoodThinking(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo val thinking: String
)
