package com.jetbrains.spacetutorial.feature.rocketlaunch

import com.jetbrains.spacetutorial.network.SpaceXSDK
import com.jetbrains.spacetutorial.ui.RocketLaunchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface IRocketLaunchViewModel {
    val state: StateFlow<RocketLaunchUiState>
    suspend fun load()
}

class CommonRocketLaunchViewModel(private val sdk: SpaceXSDK) : IRocketLaunchViewModel {
    private val _state = MutableStateFlow<RocketLaunchUiState>(RocketLaunchUiState.Loading)
    override val state: StateFlow<RocketLaunchUiState> = _state

    override suspend fun load() {
        _state.update { RocketLaunchUiState.Loading }
        try {
            val launches = sdk.getLaunches(true)
            _state.update { RocketLaunchUiState.Success(launches = launches) }
        } catch (e: Exception) {
            _state.update { RocketLaunchUiState.Fail(e.message) }
        }
    }
}
