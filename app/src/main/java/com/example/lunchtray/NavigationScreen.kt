package com.example.lunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
fun MyAppNavigation() {

    // Create NavController
    val navController = rememberNavController()

    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()

    // Get current back stack entry
    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    // Navigation Host
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {

        // Start screen
        composable(Screen.Start.route) {
            StartOrderScreen(
                onStartOrderButtonClicked = {
                    navController.navigate(Screen.Entree.route)
                }
            )
        }

        // Entree screen
        composable(Screen.Entree.route) {
            EntreeMenuScreen(
                options = DataSource.entreeMenuItems,

                onCancelButtonClicked = {
                    navController.popBackStack()
                },

                onNextButtonClicked = {
                    navController.navigate(Screen.SideDish.route)
                },

                onSelectionChanged = {
                    // TODO: update selected entree in ViewModel
                }
            )
        }

        // Side dish screen
        composable(Screen.SideDish.route) {
            SideDishMenuScreen(
                options = DataSource.sideDishMenuItems,

                onCancelButtonClicked = {
                    navController.popBackStack()
                },

                onNextButtonClicked = {
                    navController.navigate(Screen.Accompaniment.route)
                },

                onSelectionChanged = {
                    // TODO: update selected side dish in ViewModel
                }
            )
        }

        // Accompaniment screen
        composable(Screen.Accompaniment.route) {
            AccompanimentMenuScreen(
                options = DataSource.accompanimentMenuItems,

                onCancelButtonClicked = {
                    navController.popBackStack()
                },

                onNextButtonClicked = {
                    navController.navigate(Screen.Checkout.route)
                },

                onSelectionChanged = {
                    // TODO: update selected accompaniment in ViewModel
                }
            )
        }

        // Checkout screen
        composable(Screen.Checkout.route) {

            val orderUiState =
                viewModel.uiState.collectAsState().value

            CheckoutScreen(
                orderUiState = orderUiState,

                onCancelButtonClicked = {
                    navController.popBackStack()
                },

                onNextButtonClicked = {
                    navController.popBackStack(
                        Screen.Start.route,
                        inclusive = false
                    )
                }
            )
        }
    }
}


// -----------------------------
// Screen Routes
// -----------------------------


sealed class Screen(
    val route: String,
    @StringRes val title: Int
) {

    object Start : Screen("start", R.string.start_order)
    object Entree : Screen("entree", R.string.choose_entree)
    object SideDish : Screen("sideDish", R.string.choose_side_dish)
    object Accompaniment : Screen("accompaniment", R.string.choose_accompaniment)
    object Checkout : Screen("checkout", R.string.order_checkout)
}


// -----------------------------
// Initial Screen
// -----------------------------

@Composable
fun InitialScreen(
    onNextButtonClicked: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Initial Screen"
        )

        Button(
            onClick = onNextButtonClicked
        ) {
            Text(
                text = "Next"
            )
        }
    }
}


// -----------------------------
// Destination 1 Screen
// -----------------------------

@Composable
fun Destination1Screen(
    onBackButtonClicked: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Destination 1"
        )

        Button(
            onClick = onBackButtonClicked
        ) {
            Text(
                text = "Back"
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppBar(
    currentScreen: Screen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreen.title))},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }

}})}