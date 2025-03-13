package com.goldcompany.apps.goodthinking.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.goldcompany.apps.goodthinking.data.db.AppDatabase
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWord
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWordDao
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
class GoodWordDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var goodWordDao: GoodWordDao
    private val goodWord1 = GoodWord(1L, "1")
    private val goodWord2 = GoodWord(2L, "2")
    private val goodWord3 = GoodWord(3L, "3")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        goodWordDao = database.goodWordDao()

        goodWordDao.insertGoodWord(goodWord1)
        goodWordDao.insertGoodWord(goodWord2)
        goodWordDao.insertGoodWord(goodWord3)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun get_goodThinkingList() = runBlocking {
        val thinkingList = goodWordDao.getAllGoodWord().first()
        assertThat(thinkingList.size, equalTo(3))

        assertThat(thinkingList[0], equalTo(goodWord1))
        assertThat(thinkingList[1], equalTo(goodWord2))
        assertThat(thinkingList[2], equalTo(goodWord3))
    }

    @Test
    fun insert_goodThinking() = runBlocking {
        assertThat(goodWordDao.getAllGoodWord().first().size, equalTo(3))

        goodWordDao.insertGoodWord(GoodWord(4L, "4"))
        assertThat(goodWordDao.getAllGoodWord().first().size, equalTo(4))
    }

    @Test
    fun delete_goodThinking() = runBlocking {
        assertThat(goodWordDao.getAllGoodWord().first().size, equalTo(3))

        goodWordDao.deleteGoodWord(3L)
        assertThat(goodWordDao.getAllGoodWord().first().size, equalTo(2))
    }
}