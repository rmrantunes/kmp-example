package com.jetbrains.spacetutorial.di

import com.jetbrains.spacetutorial.network.SpaceXSDK
import com.jetbrains.spacetutorial.cache.AndroidDatabaseDriverFactory
import com.jetbrains.spacetutorial.feature.rocketlaunch.RocketLaunchViewModel
import com.jetbrains.spacetutorial.feature.rocketlaunch.CommonRocketLaunchViewModel
import com.jetbrains.spacetutorial.network.SpaceXApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SpaceXApi> { SpaceXApi() }
    single<SpaceXSDK> {
        SpaceXSDK(
            AndroidDatabaseDriverFactory(
                androidContext()
            ),
            get()
        )
    }
    single<CommonRocketLaunchViewModel> { CommonRocketLaunchViewModel(get()) }
    viewModel {
        RocketLaunchViewModel(get<CommonRocketLaunchViewModel>())
    }
}