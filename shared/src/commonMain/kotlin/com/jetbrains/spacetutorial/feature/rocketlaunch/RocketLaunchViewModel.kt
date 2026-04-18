package com.jetbrains.spacetutorial.feature.rocketlaunch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetbrains.spacetutorial.data.repository.OfflineFirstSpaceXLaunchesRepository
import com.jetbrains.spacetutorial.ui.RocketLaunchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface IRocketLaunchViewModel {
    val state: StateFlow<RocketLaunchUiState>
    fun load()
}

class RocketLaunchViewModel(val spaceXLaunchesRepository: OfflineFirstSpaceXLaunchesRepository) :
    ViewModel(),
    IRocketLaunchViewModel {
    private val _state = MutableStateFlow<RocketLaunchUiState>(RocketLaunchUiState.Loading)
    override val state: StateFlow<RocketLaunchUiState> = _state
        .onStart {
            if (_state.value !is RocketLaunchUiState.Success) {
                load()
            }

            viewModelScope.launch {
                observe()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            RocketLaunchUiState.Loading
        )

    suspend fun observe() {
        spaceXLaunchesRepository.getAllLaunchesStream().collect { launches ->
            if (_state.value is RocketLaunchUiState.Success) {
                _state.update { RocketLaunchUiState.Success(launches) }
            }
        }
    }

    override fun load() {
        viewModelScope.launch {
            _state.update { RocketLaunchUiState.Loading }
            try {
                spaceXLaunchesRepository.sync()
                _state.update { RocketLaunchUiState.Success() }
            }
            catch (e: Exception) {
                _state.update { RocketLaunchUiState.Fail(e.message) }
            }
        }
    }
}
