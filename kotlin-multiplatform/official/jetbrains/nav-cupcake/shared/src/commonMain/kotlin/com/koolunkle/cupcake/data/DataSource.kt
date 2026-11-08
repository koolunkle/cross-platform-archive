package com.koolunkle.cupcake.data

import com.koolunkle.cupcake.resources.Res
import com.koolunkle.cupcake.resources.chocolate
import com.koolunkle.cupcake.resources.coffee
import com.koolunkle.cupcake.resources.one_cupcake
import com.koolunkle.cupcake.resources.red_velvet
import com.koolunkle.cupcake.resources.salted_caramel
import com.koolunkle.cupcake.resources.six_cupcakes
import com.koolunkle.cupcake.resources.twelve_cupcakes
import com.koolunkle.cupcake.resources.vanilla

/** 주문 화면에서 선택할 수 있는 고정 옵션 목록. */
object DataSource {
    val flavors = listOf(
        Res.string.vanilla,
        Res.string.red_velvet,
        Res.string.chocolate,
        Res.string.salted_caramel,
        Res.string.coffee,
    )

    val quantityOptions = listOf(
        Res.string.one_cupcake to 1,
        Res.string.six_cupcakes to 6,
        Res.string.twelve_cupcakes to 12,
    )
}
