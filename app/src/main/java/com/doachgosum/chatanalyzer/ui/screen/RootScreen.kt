package com.doachgosum.chatanalyzer.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doachgosum.chatanalyzer.DataRepo
import com.doachgosum.chatanalyzer.GnbNavControllerHelper
import com.doachgosum.chatanalyzer.Screen

@Composable
fun RootScreen(
    innerPadding: PaddingValues
) {
    val rootNavController = rememberNavController()
    val gnbNavControllerHelper = remember { GnbNavControllerHelper(rootNavController) }

    NavHost(
        navController = rootNavController,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        startDestination = Screen.Gnb.route
    ) {
        composable(
            route = Screen.Gnb.route
        ) {
            GnbScreen(
                onUpdateNavController = { navController ->
                    gnbNavControllerHelper.setNavController(navController)
                },
                rootNavController = rootNavController
            )
        }

        composable(
            route = Screen.ChatDetail.routeWithArgs,
            arguments = Screen.ChatDetail.args
        ) {
            val key = it.arguments?.getString(Screen.ChatDetail.ARG_CHAT) ?: return@composable
            val chatMeta = DataRepo.loadChat(key) ?: return@composable
            ChatDetailScreen(chatMeta)
        }
    }
}
