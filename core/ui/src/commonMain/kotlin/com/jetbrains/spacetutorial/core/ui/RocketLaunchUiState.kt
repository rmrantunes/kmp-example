package com.jetbrains.spacetutorial.core.ui

import com.jetbrains.spacetutorial.core.model.RocketLaunch


sealed interface RocketLaunchUiState {
    data object Loading : RocketLaunchUiState

    data class Success(
        val launches: List<RocketLaunch> = emptyList()
    ): RocketLaunchUiState

    data class Fail(
        val message: String?
    ): RocketLaunchUiState
}