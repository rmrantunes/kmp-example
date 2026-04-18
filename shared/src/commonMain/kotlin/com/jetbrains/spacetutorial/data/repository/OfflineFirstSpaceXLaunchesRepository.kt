package com.jetbrains.spacetutorial.data.repository

import com.jetbrains.spacetutorial.cache.Database
import com.jetbrains.spacetutorial.cache.DatabaseDriverFactory
import com.jetbrains.spacetutorial.model.RocketLaunch
import com.jetbrains.spacetutorial.network.SpaceXApi
import com.jetbrains.spacetutorial.network.model.toModel

class OfflineFirstSpaceXLaunchesRepository(databaseDriverFactory: DatabaseDriverFactory, val api: SpaceXApi) {
    private val database = Database(databaseDriverFactory)

    @Throws(Exception::class)
    suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedValues = database.getAllLaunches()
        return if (cachedValues.isNotEmpty() && !forceReload) {
            cachedValues
        } else {
            api.getAllLaunches().map { it.toModel() }.also {
                database.clearAndCreateLaunches(it)
            }
        }
    }
}