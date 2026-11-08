package com.koolunkle.cupcake.data

/** 주문 진행 상태. 화면 간 이동 중에도 ViewModel이 이 값을 유지합니다. */
data class OrderUiState(
    /** 선택한 수량 (1, 6, 12) */
    val quantity: Int = 0,
    /** 선택한 맛 */
    val flavor: String = "",
    /** 선택한 픽업 날짜 (예: 2026-11-08) */
    val date: String = "",
    /** 화면에 표시할 총 가격 (예: $12.00) */
    val price: String = "",
    /** 선택 가능한 픽업 날짜. 첫 번째 값이 당일입니다. */
    val pickupOptions: List<String> = emptyList(),
)
