package com.koolunkle.cupcake.ui

import androidx.lifecycle.ViewModel
import com.koolunkle.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToLong
import kotlin.time.Clock

/** 컵케이크 1개 가격 */
private const val PRICE_PER_CUPCAKE = 2.00

/** 당일 픽업 추가 요금 */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/** 픽업 가능 일수 (오늘 포함) */
private const val PICKUP_DAYS = 4

/**
 * 수량, 맛, 픽업 날짜로 이루어진 주문 상태를 보관하고 총 가격을 계산합니다.
 * 공통 코드(commonMain)에 있으므로 Android, Desktop, Web이 같은 로직을 사용합니다.
 */
class OrderViewModel(
    private val clock: Clock = Clock.System,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(newOrder())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { it.copy(quantity = numberCupcakes).withPrice() }
    }

    fun setFlavor(desiredFlavor: String) {
        _uiState.update { it.copy(flavor = desiredFlavor) }
    }

    fun setDate(pickupDate: String) {
        _uiState.update { it.copy(date = pickupDate).withPrice() }
    }

    fun resetOrder() {
        _uiState.value = newOrder()
    }

    private fun newOrder() = OrderUiState(pickupOptions = pickupOptions()).withPrice()

    // 원본은 계산할 때마다 날짜 목록을 새로 만들어 비교했지만, 여기서는 상태에 저장된 목록의 첫 날짜(당일)와 비교합니다.
    private fun OrderUiState.withPrice(): OrderUiState {
        var total = quantity * PRICE_PER_CUPCAKE
        if (date.isNotEmpty() && date == pickupOptions.firstOrNull()) {
            total += PRICE_FOR_SAME_DAY_PICKUP
        }
        return copy(price = formatPrice(total))
    }

    /** 오늘부터 [PICKUP_DAYS]일간의 날짜 목록 (ISO 형식) */
    private fun pickupOptions(): List<String> {
        val now = clock.now()
        return List(PICKUP_DAYS) { day ->
            now.plus(day, DateTimeUnit.DAY, timeZone).toLocalDateTime(timeZone).date.toString()
        }
    }
}

/** 공통 코드에는 String.format이 없으므로 소수점 둘째 자리까지 직접 맞춥니다. */
internal fun formatPrice(amount: Double): String {
    val cents = (amount * 100).roundToLong()
    return "$${cents / 100}.${(cents % 100).toString().padStart(2, '0')}"
}
