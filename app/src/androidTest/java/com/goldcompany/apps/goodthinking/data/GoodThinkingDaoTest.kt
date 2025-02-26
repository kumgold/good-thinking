package com.goldcompany.apps.goodthinking.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.goldcompany.apps.goodthinking.data.db.AppDatabase
import com.goldcompany.apps.goodthinking.data.db.GoodThinking
import com.goldcompany.apps.goodthinking.data.db.GoodThinkingDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GoodThinkingDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var goodThinkingDao: GoodThinkingDao
    private val goodThinking1 = GoodThinking(1L, "1")
    private val goodThinking2 = GoodThinking(2L, "2")
    private val goodThinking3 = GoodThinking(3L, "3")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        goodThinkingDao = database.goodThinkingDao()

        goodThinkingDao.insertGoodThinking(goodThinking1)
        goodThinkingDao.insertGoodThinking(goodThinking2)
        goodThinkingDao.insertGoodThinking(goodThinking3)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun get_goodThinkingList() = runBlocking {
        val thinkingList = goodThinkingDao.getAllThinking().first()
        assertThat(thinkingList.size, equalTo(3))

        assertThat(thinkingList[0], equalTo(goodThinking1))
        assertThat(thinkingList[1], equalTo(goodThinking2))
        assertThat(thinkingList[2], equalTo(goodThinking3))
    }

    @Test
    fun insert_goodThinking() = runBlocking {
        assertThat(goodThinkingDao.getAllThinking().first().size, equalTo(3))

        goodThinkingDao.insertGoodThinking(GoodThinking(4L, "4"))
        assertThat(goodThinkingDao.getAllThinking().first().size, equalTo(4))
    }

    @Test
    fun delete_goodThinking() = runBlocking {
        assertThat(goodThinkingDao.getAllThinking().first().size, equalTo(3))

        goodThinkingDao.deleteGoodThinking(3L)
        assertThat(goodThinkingDao.getAllThinking().first().size, equalTo(2))
    }
}