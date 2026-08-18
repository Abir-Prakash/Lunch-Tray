package com.example.lunchtray

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayApp() {

    val viewModel: OrderViewModel = viewModel()

    Scaffold(
        topBar = {
            LunchTrayAppBar(
                currentScreen = Screen.Start,
                canNavigateBack = false,
                navigateUp = { }
            )
        }
    ) { innerPadding ->

        val uiState by viewModel.uiState.collectAsState()

        MyAppNavigation()
    }
}