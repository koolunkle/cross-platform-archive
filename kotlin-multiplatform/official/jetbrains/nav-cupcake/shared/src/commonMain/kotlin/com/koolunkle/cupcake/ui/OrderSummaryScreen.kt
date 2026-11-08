package com.koolunkle.cupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.koolunkle.cupcake.data.OrderUiState
import com.koolunkle.cupcake.resources.Res
import com.koolunkle.cupcake.resources.cancel
import com.koolunkle.cupcake.resources.cupcakes
import com.koolunkle.cupcake.resources.flavor
import com.koolunkle.cupcake.resources.new_cupcake_order
import com.koolunkle.cupcake.resources.order_details
import com.koolunkle.cupcake.resources.pickup_date
import com.koolunkle.cupcake.resources.quantity
import com.koolunkle.cupcake.resources.send
import com.koolunkle.cupcake.ui.components.FormattedPriceLabel
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

/**
 * 주문 내역을 보여주는 마지막 화면.
 * Send 버튼을 누르면 [onSendButtonClicked]에 제목과 요약 문자열을 전달합니다.
 */
@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (subject: String, summary: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // 원본의 TODO(복수형 처리)를 plurals 리소스로 구현했습니다.
    val numberOfCupcakes = pluralStringResource(Res.plurals.cupcakes, orderUiState.quantity, orderUiState.quantity)
    // 원본은 Total 자리에 수량을 넘기고 있어 가격으로 바로잡았습니다.
    val orderSummary = stringResource(
        Res.string.order_details,
        numberOfCupcakes,
        orderUiState.flavor,
        orderUiState.date,
        orderUiState.price,
    )
    val newOrder = stringResource(Res.string.new_cupcake_order)
    val items = listOf(
        stringResource(Res.string.quantity) to numberOfCupcakes,
        stringResource(Res.string.flavor) to orderUiState.flavor,
        stringResource(Res.string.pickup_date) to orderUiState.date,
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items.forEach { (label, value) ->
                Text(label.uppercase())
                Text(text = value, fontWeight = FontWeight.Bold)
                HorizontalDivider()
            }
            FormattedPriceLabel(
                subtotal = orderUiState.price,
                modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
            )
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSendButtonClicked(newOrder, orderSummary) },
            ) {
                Text(stringResource(Res.string.send))
            }
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCancelButtonClicked,
            ) {
                Text(stringResource(Res.string.cancel))
            }
        }
    }
}
