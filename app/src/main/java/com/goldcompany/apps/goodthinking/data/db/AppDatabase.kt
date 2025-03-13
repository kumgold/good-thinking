package com.goldcompany.apps.goodthinking.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.goldcompany.apps.goodthinking.data.db.chat.ChatMessageDao
import com.goldcompany.apps.goodthinking.data.db.chat.ChatMessageLocal
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWord
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWordDao

@Database(entities = [GoodWord::class, ChatMessageLocal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun goodWordDao(): GoodWordDao
    abstract fun chatMessageDao(): ChatMessageDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "gt_database")
                .build()
        }
    }
}