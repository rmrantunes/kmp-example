package com.jetbrains.spacetutorial

import androidx.compose.runtime.Composable
import com.jetbrains.spacetutorial.feature.rocketlaunch.RocketLaunchScreen
import com.jetbrains.spacetutorial.core.designsystem.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        RocketLaunchScreen()
    }
}