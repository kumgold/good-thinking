package com.goldcompany.apps.goodthinking.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "good_thinking", primaryKeys = ["id"])
class GoodThinking(
    @ColumnInfo val id: Long,
    @ColumnInfo val thinking: String
)
