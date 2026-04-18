package com.jetbrains.spacetutorial.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jetbrains.spacetutorial.cache.IOSDatabaseDriverFactory
import com.jetbrains.spacetutorial.feature.rocketlaunch.RocketLaunchViewModel
import com.jetbrains.spacetutorial.network.SpaceXApi
import com.jetbrains.spacetutorial.network.SpaceXSDK
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper : KoinComponent {

    val rocketLaunchViewModelFactory by lazy {
        val spaceXSDK by inject<SpaceXSDK>()

        viewModelFactory {
            initializer { RocketLaunchViewModel(spaceXSDK) }
        }
    }

    companion object {
        fun start() {
            startKoin {
                modules(module {
                    single<SpaceXApi> { SpaceXApi() }
                    single<SpaceXSDK> {
                        SpaceXSDK(
                            databaseDriverFactory = IOSDatabaseDriverFactory(), api = get()
                        )
                    }
                })
            }
        }
    }
}
