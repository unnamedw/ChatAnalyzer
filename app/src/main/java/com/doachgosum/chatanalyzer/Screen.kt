package com.doachgosum.chatanalyzer

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.doachgosum.chatanalyzer.chat.ChatMeta
import com.doachgosum.chatanalyzer.util.hasBackstack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface Screen {
    val route: String

    data object Gnb: Screen {
        override val route: String = "GnbScreen"
    }

    data object Test: Screen {
        override val route: String = "TestScreen"
    }

    data object ChatDetail: Screen {
        override val route: String = "ChatDetailScreen"
        const val ARG_CHAT = "ARG_CHAT"

        val routeWithArgs = "${route}?${ARG_CHAT}={${ARG_CHAT}}"

        val args = listOf(
            navArgument(ARG_CHAT) {
                type = NavType.StringType
                defaultValue = ""
            },
        )

        fun route(chatKey: String) = "$route?$ARG_CHAT=$chatKey"
    }
}

internal class GnbNavControllerHelper(
    private val rootNavController: NavHostController
) {
    private var navController: MutableStateFlow<NavHostController?> = MutableStateFlow(null)

    fun setNavController(navHostController: NavHostController?) {
        navController.update { navHostController }
    }

    fun getNavController(): NavHostController? {
        if (!rootNavController.hasBackstack(Screen.Gnb.route)) {
            return null
        }
        return navController.value
    }
}