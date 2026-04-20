package com.jetbrains.spacetutorial.core.data.repository

import com.jetbrains.spacetutorial.core.database.Database
import com.jetbrains.spacetutorial.core.database.DatabaseDriverFactory
import com.jetbrains.spacetutorial.core.model.RocketLaunch
import com.jetbrains.spacetutorial.core.network.spacexapi.SpaceXApi
import com.jetbrains.spacetutorial.core.network.spacexapi.model.toModel
import kotlinx.coroutines.flow.Flow

class OfflineFirstSpaceXLaunchesRepository(
    databaseDriverFactory: DatabaseDriverFactory,
    val api: SpaceXApi
) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    fun getAllLaunchesStream(): Flow<List<RocketLaunch>> {
        return database.getAllLaunchesStream()
    }

    @Throws(Exception::class)
    suspend fun sync() {
        api.getAllLaunches().map { it.toModel() }.also {
            database.clearAndCreateLaunches(it)
        }
    }
}