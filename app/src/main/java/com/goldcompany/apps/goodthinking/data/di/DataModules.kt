package com.goldcompany.apps.goodthinking.data.di

import android.content.Context
import com.goldcompany.apps.goodthinking.data.db.AppDatabase
import com.goldcompany.apps.goodthinking.data.db.GoodThinkingDao
import com.goldcompany.apps.goodthinking.data.repo.DefaultRepository
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
    ): GoodThinkingDao = appDatabase.goodThinkingDao()
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideDefaultRepository(
        goodThinkingDao: GoodThinkingDao
    ): DefaultRepository = DefaultRepository(goodThinkingDao)
}