package com.example.yandex_school_app

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.common.store.AppTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testThemeSwitch() {
        fun SemanticsNodeInteraction.assertTextColor(expectedColor: Color) {
            val bitmap = this.captureToImage().asAndroidBitmap()
            val pixelColor = bitmap.getPixel(
                bitmap.width / 2,
                bitmap.height / 2
            )
            val actualColor = Color(pixelColor)

            assert(actualColor == expectedColor) {
                "Expected text color $expectedColor, but was $actualColor"
            }
        }

        composeTestRule.setContent {
            var isDark by remember { mutableStateOf(false) }
            MaterialTheme(
                colorScheme = AppTheme.colorScheme(
                    isDark,
                    AppTheme.PrimaryColorVariant.GREEN
                )
            ) {
                Column {
                    Text(
                        text = "Sample text",
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.testTag("sampleText")
                    )
                    Switch(
                        checked = isDark,
                        onCheckedChange = { isDark = it },
                        modifier = Modifier.testTag("themeSwitch")
                    )
                }
            }
        }

        val lightColor =
            AppTheme.colorScheme(false, AppTheme.PrimaryColorVariant.GREEN).onBackground
        val darkColor = AppTheme.colorScheme(true, AppTheme.PrimaryColorVariant.GREEN).onBackground

        composeTestRule.onNodeWithTag("sampleText")
            .assertExists()
            .assertTextColor(lightColor)

        composeTestRule.onNodeWithTag("themeSwitch")
            .performClick()

        composeTestRule.onNodeWithTag("sampleText")
            .assertTextColor(darkColor)
    }

    @Test
    fun testPinInput() {
        composeTestRule.setContent {
            var pin by remember { mutableStateOf("") }
            Column {
                TextField(
                    value = pin,
                    onValueChange = { if (it.length <= 4) pin = it },
                    label = { Text("Enter PIN") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.testTag("pinInput")
                )

                Text(
                    text = "Введен PIN: $pin",
                    modifier = Modifier.testTag("pinDisplay")
                )
            }
        }

        val pinInput = composeTestRule.onNodeWithTag("pinInput")
        pinInput.assertExists()
        pinInput.assertTextEquals("")

        pinInput.performTextInput("1")
        composeTestRule.onNodeWithTag("pinDisplay").assertTextEquals("Введен PIN: 1")

        pinInput.performTextInput("2")
        composeTestRule.onNodeWithTag("pinDisplay").assertTextEquals("Введен PIN: 12")

        pinInput.performTextInput("3")
        composeTestRule.onNodeWithTag("pinDisplay").assertTextEquals("Введен PIN: 123")

        pinInput.performTextInput("4")
        composeTestRule.onNodeWithTag("pinDisplay").assertTextEquals("Введен PIN: 1234")

        pinInput.performTextInput("5")
        composeTestRule.onNodeWithTag("pinDisplay").assertTextEquals("Введен PIN: 1234")
    }
}