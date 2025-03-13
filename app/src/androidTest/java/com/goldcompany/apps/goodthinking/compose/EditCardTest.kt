package com.goldcompany.apps.goodthinking.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.goldcompany.apps.goodthinking.data.db.goodword.GoodWord
import com.goldcompany.apps.goodthinking.feature.editcard.EditCardScreen
import com.goldcompany.apps.goodthinking.utilities.testGoodWord
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun card_emptyCard() {
        startEditCard(emptyList())
        composeTestRule.onNodeWithText("좋은 생각을 추가하세요").assertIsDisplayed()
    }

    @Test
    fun card_notEmptyCard() {
        startEditCard(listOf(testGoodWord))
        composeTestRule.onNodeWithText("좋은 생각을 추가하세요").assertDoesNotExist()
        composeTestRule.onNodeWithText(testGoodWord.word).assertIsDisplayed()
    }

    private fun startEditCard(thinkingList: List<GoodWord>) {
        composeTestRule.setContent {
            EditCardScreen(thinkingList = thinkingList)
        }
    }
}