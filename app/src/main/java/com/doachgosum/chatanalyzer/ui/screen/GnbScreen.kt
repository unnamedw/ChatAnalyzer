package com.doachgosum.chatanalyzer.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doachgosum.chatanalyzer.Screen

@Composable
fun GnbScreen(
    onUpdateNavController: (NavHostController) -> Unit = {},
    rootNavController: NavController
) {
    val gnbNavController = rememberNavController()

    LaunchedEffect(gnbNavController) {
        onUpdateNavController.invoke(gnbNavController)
    }

    NavHost(
        navController = gnbNavController,
        modifier = Modifier
            .fillMaxSize(),
        startDestination = Screen.Test.route
    ) {
        composable(Screen.Test.route) {
            TestScreen(rootNavController = rootNavController)
        }
    }
}