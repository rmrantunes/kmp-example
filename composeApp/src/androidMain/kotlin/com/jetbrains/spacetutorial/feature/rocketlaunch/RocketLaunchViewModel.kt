package com.jetbrains.spacetutorial.feature.rocketlaunch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RocketLaunchViewModel(sharedVm: IRocketLaunchViewModel) : ViewModel(),
    IRocketLaunchViewModel by sharedVm {
    init {
        viewModelScope.launch {
            sharedVm.load()
        }
    }
}
