package com.jetbrains.spacetutorial.data.repository

import com.jetbrains.spacetutorial.cache.Database
import com.jetbrains.spacetutorial.cache.DatabaseDriverFactory
import com.jetbrains.spacetutorial.model.RocketLaunch
import com.jetbrains.spacetutorial.network.SpaceXApi
import com.jetbrains.spacetutorial.network.model.toModel
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