package com.jetbrains.spacetutorial.feature.rocketlaunch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RocketLaunchViewModel(val sharedVm: SharedRocketLaunchViewModel) : ViewModel() {
    init {
        viewModelScope.launch {
            sharedVm.load()
        }
    }
}
