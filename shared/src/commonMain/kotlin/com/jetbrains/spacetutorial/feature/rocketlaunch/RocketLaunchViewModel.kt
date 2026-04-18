package com.jetbrains.spacetutorial.feature.rocketlaunch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.spacetutorial.network.SpaceXSDK
import com.jetbrains.spacetutorial.ui.RocketLaunchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface IRocketLaunchViewModel {
    val state: StateFlow<RocketLaunchUiState>
    fun load()
}

class RocketLaunchViewModel(val sdk: SpaceXSDK) : ViewModel(),
    IRocketLaunchViewModel {
    private val _state = MutableStateFlow<RocketLaunchUiState>(RocketLaunchUiState.Loading)
    override val state: StateFlow<RocketLaunchUiState> = _state.onStart { load() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        RocketLaunchUiState.Loading
    )

    override fun load() {
        viewModelScope.launch {
            _state.update { RocketLaunchUiState.Loading }
            try {
                val launches = sdk.getLaunches(true)
                _state.update { RocketLaunchUiState.Success(launches = launches) }
            } catch (e: Exception) {
                _state.update { RocketLaunchUiState.Fail(e.message) }
            }
        }
    }
}
