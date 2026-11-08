package com.koolunkle.cupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.koolunkle.cupcake.resources.Res
import com.koolunkle.cupcake.resources.cancel
import com.koolunkle.cupcake.resources.next
import com.koolunkle.cupcake.ui.components.FormattedPriceLabel
import org.jetbrains.compose.resources.stringResource

/**
 * 라디오 버튼 목록에서 하나를 고르는 화면. 맛 선택과 픽업 날짜 선택에 함께 재사용합니다.
 * 선택값이 있어야 Next 버튼이 활성화됩니다.
 */
@Composable
fun SelectOptionScreen(
    subtotal: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.padding(16.dp).selectableGroup()) {
            options.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selectedValue == item,
                            role = Role.RadioButton,
                            onClick = {
                                selectedValue = item
                                onSelectionChanged(item)
                            },
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Row 전체가 클릭을 처리하므로 RadioButton 자체의 onClick은 비워 둡니다.
                    RadioButton(selected = selectedValue == item, onClick = null)
                    Text(item, modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 12.dp))
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            FormattedPriceLabel(
                subtotal = subtotal,
                modifier = Modifier.align(Alignment.End),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked,
            ) {
                Text(stringResource(Res.string.cancel))
            }
            Button(
                modifier = Modifier.weight(1f),
                enabled = selectedValue.isNotEmpty(),
                onClick = onNextButtonClicked,
            ) {
                Text(stringResource(Res.string.next))
            }
        }
    }
}
