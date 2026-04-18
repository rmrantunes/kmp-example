package com.jetbrains.spacetutorial.network.model

import com.jetbrains.spacetutorial.model.Links
import com.jetbrains.spacetutorial.model.Patch
import com.jetbrains.spacetutorial.model.RocketLaunch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class NetworkRocketLaunch(
    @SerialName("flight_number")
    val flightNumber: Int,
    @SerialName("name")
    val missionName: String,
    @SerialName("date_utc")
    val launchDateUTC: String,
    @SerialName("details")
    val details: String?,
    @SerialName("success")
    val launchSuccess: Boolean?,
    @SerialName("links")
    val links: NetworkRocketLaunchLinks
) {
}

fun NetworkRocketLaunch.toModel(): RocketLaunch {
    return RocketLaunch(
        flightNumber,
        missionName,
        launchDateUTC,
        details,
        launchSuccess,
        links = Links(
            links.patch?.let { Patch(links.patch.small, links.patch.large) },
            links.article
        )
    )
}

@Serializable
data class NetworkRocketLaunchLinks(
    @SerialName("patch")
    val patch: NetworkRocketLaunchPatch?,
    @SerialName("article")
    val article: String?
)

@Serializable
data class NetworkRocketLaunchPatch(
    @SerialName("small")
    val small: String?,
    @SerialName("large")
    val large: String?
)