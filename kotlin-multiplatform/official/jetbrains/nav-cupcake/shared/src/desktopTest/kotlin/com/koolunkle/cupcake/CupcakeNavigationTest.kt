package com.koolunkle.cupcake

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.koolunkle.cupcake.ui.OrderViewModel
import com.koolunkle.cupcake.ui.theme.CupcakeTheme
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.Instant

/** Desktop(JVM)에서 실제 화면을 구성해 주문 흐름 전체를 따라가는 UI 테스트. */
@OptIn(ExperimentalTestApi::class)
class CupcakeNavigationTest {

    private val fixedClock = object : Clock {
        override fun now() = Instant.parse("2026-11-08T12:00:00Z")
    }

    @Test
    fun orderFlowReachesSummaryWithCorrectTotal() = runComposeUiTest {
        setContent {
            CupcakeTheme {
                CupcakeApp(viewModel = OrderViewModel(clock = fixedClock, timeZone = TimeZone.UTC))
            }
        }

        // Start → Flavor
        onNodeWithText("Six Cupcakes").performClick()
        onNodeWithText("Choose Flavor").assertExists()
        onNodeWithText("Next").assertIsNotEnabled()
        onNodeWithText("Chocolate").performClick()
        onNodeWithText("Next").performClick()

        // Flavor → Pickup: 당일(2026-11-08)을 고르면 추가 요금 $3이 붙습니다.
        onNodeWithText("Choose Pickup Date").assertExists()
        onNodeWithText("2026-11-08").performClick()
        onNodeWithText("Next").performClick()

        // Pickup → Summary
        onNodeWithText("Order Summary").assertExists()
        onNodeWithText("6 cupcakes").assertExists()
        onNodeWithText("Subtotal $15.00").assertExists()
    }

    @Test
    fun cancelReturnsToStart() = runComposeUiTest {
        setContent {
            CupcakeTheme { CupcakeApp() }
        }

        onNodeWithText("One Cupcake").performClick()
        onAllNodesWithText("Cancel").onFirst().performClick()
        onNodeWithText("Order Cupcakes").assertExists()
    }
}
