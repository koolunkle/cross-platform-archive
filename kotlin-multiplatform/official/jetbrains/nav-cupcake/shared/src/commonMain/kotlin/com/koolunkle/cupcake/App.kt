package com.koolunkle.cupcake

import androidx.compose.runtime.Composable
import com.koolunkle.cupcake.ui.theme.CupcakeTheme

/** 모든 플랫폼의 진입점이 호출하는 루트 Composable. */
@Composable
fun App() {
    CupcakeTheme {
        CupcakeApp()
    }
}
