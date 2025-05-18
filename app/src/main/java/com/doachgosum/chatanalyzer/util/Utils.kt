package com.doachgosum.chatanalyzer.util

import androidx.navigation.NavController

fun NavController.hasBackstack(route: String): Boolean {
    return runCatching { getBackStackEntry(route) }.getOrNull() != null
}