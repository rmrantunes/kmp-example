package com.jetbrains.spacetutorial.di

import com.jetbrains.spacetutorial.network.SpaceXSDK
import com.jetbrains.spacetutorial.cache.IOSDatabaseDriverFactory
import com.jetbrains.spacetutorial.feature.rocketlaunch.CommonRocketLaunchViewModel
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper : KoinComponent {
    val rocketLaunchViewModel: CommonRocketLaunchViewModel by inject()

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
                    single<CommonRocketLaunchViewModel> { CommonRocketLaunchViewModel(get()) }
                })
            }
        }
    }
}
