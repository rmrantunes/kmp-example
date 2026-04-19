package com.jetbrains.spacetutorial.ui

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