package com.koolunkle.cupcake.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.koolunkle.cupcake.resources.Res
import com.koolunkle.cupcake.resources.cupcake
import com.koolunkle.cupcake.resources.order_cupcakes
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * 첫 화면. 수량 버튼을 누르면 [onNextButtonClicked]에 선택한 수량을 전달합니다.
 * 화면은 상태를 직접 갖지 않고 콜백으로만 상위(NavHost)에 이벤트를 알립니다.
 */
@Composable
fun StartOrderScreen(
    quantityOptions: List<Pair<StringResource, Int>>,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Image(
                painter = painterResource(Res.drawable.cupcake),
                contentDescription = null,
            )
            Text(
                text = stringResource(Res.string.order_cupcakes),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            quantityOptions.forEach { (label, quantity) ->
                Button(
                    onClick = { onNextButtonClicked(quantity) },
                    modifier = Modifier.fillMaxWidth().widthIn(min = 250.dp),
                ) {
                    Text(stringResource(label))
                }
            }
        }
    }
}
