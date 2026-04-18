package br.com.auttar.sampleauttarsdk.util

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(direction: NavDirections) {
    graph.getAction(direction.actionId)?.run { navigate(direction) }
}