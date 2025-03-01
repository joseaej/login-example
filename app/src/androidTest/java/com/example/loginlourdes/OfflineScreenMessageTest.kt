package com.example.loginlourdes

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginlourdes.Base.ui.OfflineScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OfflineScreenMessageTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `muestra mensaje offline true`(){
        composeTestRule.setContent {
            MaterialTheme {
                OfflineScreen()
            }
        }
        val offlineMessage = composeTestRule.activity.getString(R.string.message_offline)
        composeTestRule.onNodeWithText(offlineMessage).isDisplayed()
    }
}