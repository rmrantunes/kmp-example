package com.jetbrains.spacetutorial.feature.rocketlaunch

import com.jetbrains.spacetutorial.RocketLaunch
import com.jetbrains.spacetutorial.SpaceXSDK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

interface IRocketLaunchViewModel {
    val state: StateFlow<RocketLaunchScreenState>
    suspend fun load()
}

class SharedRocketLaunchViewModel(private val sdk: SpaceXSDK) : IRocketLaunchViewModel {
    private val _state = MutableStateFlow(RocketLaunchScreenState())
    override val state: StateFlow<RocketLaunchScreenState> = _state

    override suspend fun load() {
        _state.update { it.copy(isLoading = true, launches = emptyList()) }
        try {
            val launches = sdk.getLaunches(true)
            _state.update {it.copy(isLoading = false, launches = launches)}
        } catch (e: Exception) {
            _state.update {it.copy(isLoading = false, launches = emptyList())}
        }
    }
}

data class RocketLaunchScreenState(
    val isLoading: Boolean = false,
    val launches: List<RocketLaunch> = emptyList()
)