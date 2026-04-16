package com.jetbrains.spacetutorial.di

import com.jetbrains.spacetutorial.SpaceXSDK
import com.jetbrains.spacetutorial.cache.IOSDatabaseDriverFactory
import com.jetbrains.spacetutorial.feature.rocketlaunch.SharedRocketLaunchViewModel
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper : KoinComponent {
    val rocketLaunchViewModel: SharedRocketLaunchViewModel by inject()

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
                    single<SharedRocketLaunchViewModel> { SharedRocketLaunchViewModel(get()) }
                })
            }
        }
    }
}
