package com.koolunkle.cupcake.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.koolunkle.cupcake.resources.Res
import com.koolunkle.cupcake.resources.subtotal_price
import org.jetbrains.compose.resources.stringResource

/** "Subtotal $12.00" 형식으로 가격을 표시합니다. */
@Composable
fun FormattedPriceLabel(subtotal: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(Res.string.subtotal_price, subtotal),
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
    )
}
