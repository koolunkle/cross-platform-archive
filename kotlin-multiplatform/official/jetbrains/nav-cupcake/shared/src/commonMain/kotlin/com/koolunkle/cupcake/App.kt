package com.koolunkle.cupcake

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/** 모든 플랫폼의 진입점이 호출하는 루트 Composable. */
@Composable
fun App() {
    MaterialTheme {
        Text("Cupcake")
    }
}
