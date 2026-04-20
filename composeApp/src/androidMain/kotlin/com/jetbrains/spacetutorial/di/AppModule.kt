package com.jetbrains.spacetutorial.di

import com.jetbrains.spacetutorial.core.data.repository.OfflineFirstSpaceXLaunchesRepository
import com.jetbrains.spacetutorial.core.database.AndroidDatabaseDriverFactory
import com.jetbrains.spacetutorial.feature.rocketlaunch.RocketLaunchViewModel
import com.jetbrains.spacetutorial.core.network.spacexapi.SpaceXApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SpaceXApi> { SpaceXApi() }
    single<OfflineFirstSpaceXLaunchesRepository> {
        OfflineFirstSpaceXLaunchesRepository(
            AndroidDatabaseDriverFactory(
                androidContext()
            ),
            get()
        )
    }
    viewModel {
        RocketLaunchViewModel(get())
    }
}