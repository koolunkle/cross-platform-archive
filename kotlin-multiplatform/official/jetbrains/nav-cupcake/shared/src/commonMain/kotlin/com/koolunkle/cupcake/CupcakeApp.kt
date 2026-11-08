package com.koolunkle.cupcake

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.koolunkle.cupcake.data.DataSource
import com.koolunkle.cupcake.resources.Res
import com.koolunkle.cupcake.resources.app_name
import com.koolunkle.cupcake.resources.back_button
import com.koolunkle.cupcake.resources.choose_flavor
import com.koolunkle.cupcake.resources.choose_pickup_date
import com.koolunkle.cupcake.resources.ok
import com.koolunkle.cupcake.resources.order_summary
import com.koolunkle.cupcake.ui.OrderSummaryScreen
import com.koolunkle.cupcake.ui.OrderViewModel
import com.koolunkle.cupcake.ui.SelectOptionScreen
import com.koolunkle.cupcake.ui.StartOrderScreen
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/** 앱의 화면 목록. enum 이름을 Navigation 경로(route)로 사용합니다. */
enum class CupcakeScreen(val title: StringResource) {
    Start(Res.string.app_name),
    Flavor(Res.string.choose_flavor),
    Pickup(Res.string.choose_pickup_date),
    Summary(Res.string.order_summary),
}

/** 현재 화면 제목과, 뒤로 갈 수 있을 때만 뒤로 가기 버튼을 표시하는 상단 바. */
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.back_button),
                    )
                }
            }
        },
    )
}

/**
 * 화면 흐름: Start(수량) → Flavor(맛) → Pickup(날짜) → Summary(요약).
 * ViewModel 하나를 모든 화면이 공유하여 주문 상태를 유지합니다.
 */
@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel { OrderViewModel() },
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name,
    )
    val uiState by viewModel.uiState.collectAsState()
    // 공유(Intent 등)는 플랫폼마다 달라서, 공통 코드에서는 다이얼로그로 주문 내용을 보여줍니다.
    var sentOrder by remember { mutableStateOf<Pair<String, String>?>(null) }

    fun cancelOrderAndNavigateToStart() {
        viewModel.resetOrder()
        navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
    }

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
        ) {
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                )
            }
            composable(route = CupcakeScreen.Flavor.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = DataSource.flavors.map { stringResource(it) },
                    onSelectionChanged = viewModel::setFlavor,
                    onCancelButtonClicked = ::cancelOrderAndNavigateToStart,
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name) },
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = uiState.pickupOptions,
                    onSelectionChanged = viewModel::setDate,
                    onCancelButtonClicked = ::cancelOrderAndNavigateToStart,
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route = CupcakeScreen.Summary.name) {
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = ::cancelOrderAndNavigateToStart,
                    onSendButtonClicked = { subject, summary -> sentOrder = subject to summary },
                    modifier = Modifier.fillMaxHeight(),
                )
            }
        }
    }

    sentOrder?.let { (subject, summary) ->
        AlertDialog(
            onDismissRequest = { sentOrder = null },
            title = { Text(subject) },
            text = { Text(summary) },
            confirmButton = {
                TextButton(
                    onClick = {
                        sentOrder = null
                        cancelOrderAndNavigateToStart()
                    },
                ) {
                    Text(stringResource(Res.string.ok))
                }
            },
        )
    }
}
