package com.koolunkle.cupcake.ui

import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class OrderViewModelTest {

    // 2026-11-08 12:00 UTC로 고정하여 날짜에 따라 결과가 바뀌지 않게 합니다.
    private val fixedClock = object : Clock {
        override fun now() = Instant.parse("2026-11-08T12:00:00Z")
    }

    private fun viewModel() = OrderViewModel(clock = fixedClock, timeZone = TimeZone.UTC)

    @Test
    fun pickupOptionsStartFromTodayForFourDays() {
        assertEquals(
            listOf("2026-11-08", "2026-11-09", "2026-11-10", "2026-11-11"),
            viewModel().uiState.value.pickupOptions,
        )
    }

    @Test
    fun priceIsQuantityTimesUnitPrice() {
        val vm = viewModel()
        vm.setQuantity(6)
        vm.setDate("2026-11-09")
        assertEquals("$12.00", vm.uiState.value.price)
    }

    @Test
    fun sameDayPickupAddsSurcharge() {
        val vm = viewModel()
        vm.setQuantity(1)
        vm.setDate("2026-11-08")
        assertEquals("$5.00", vm.uiState.value.price)
    }

    @Test
    fun resetOrderClearsSelections() {
        val vm = viewModel()
        vm.setQuantity(12)
        vm.setFlavor("Coffee")
        vm.resetOrder()
        with(vm.uiState.value) {
            assertEquals(0, quantity)
            assertEquals("", flavor)
            assertEquals("$0.00", price)
        }
    }

    @Test
    fun formatPricePadsCents() {
        assertEquals("$2.50", formatPrice(2.5))
        assertEquals("$27.00", formatPrice(27.0))
    }
}
