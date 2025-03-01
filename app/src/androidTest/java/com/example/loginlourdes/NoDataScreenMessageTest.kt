package com.example.loginlourdes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.loginlourdes.Base.ui.NoDataScreen
import com.example.loginlourdes.Base.ui.theme.LoginLourdesTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoDataScreenMessageTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `muestra mensaje nodata true`() {
        composeTestRule.setContent {
            LoginLourdesTheme {
                NoDataScreen()
            }
        }
        val noDataMessage = composeTestRule.activity.getString(R.string.no_data_messaje)
        composeTestRule.onNodeWithText(noDataMessage).assertIsDisplayed()
    }
}
