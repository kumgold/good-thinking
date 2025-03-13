package com.goldcompany.apps.goodthinking.data.di

import android.content.Context
import com.goldcompany.apps.goodthinking.BuildConfig
import com.goldcompany.apps.goodthinking.data.db.AppDatabase
import com.goldcompany.apps.goodthinking.data.db.chat.ChatMessageDao
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWordDao
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModules {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideGoodThinkingDao(
        appDatabase: AppDatabase
    ): GoodWordDao = appDatabase.goodWordDao()

    @Singleton
    @Provides
    fun provideChatMessageDao(
        appDatabase: AppDatabase
    ): ChatMessageDao = appDatabase.chatMessageDao()

    @Singleton
    @Provides
    fun provideGenerativeModel(): GenerativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = BuildConfig.apiKey,
        generationConfig = generationConfig {
            temperature = 0.7f
        }
    )
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideDefaultRepository(
        goodWordDao: GoodWordDao,
        chatMessageDao: ChatMessageDao
    ): DefaultRepository = DefaultRepository(goodWordDao, chatMessageDao)
}