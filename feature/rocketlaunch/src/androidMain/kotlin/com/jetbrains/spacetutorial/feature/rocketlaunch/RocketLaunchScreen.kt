package com.jetbrains.spacetutorial.feature.rocketlaunch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jetbrains.spacetutorial.core.ui.RocketLaunchUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RocketLaunchScreen() {
    val viewModel = koinViewModel<RocketLaunchViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    RocketLaunchScreen(state, onRefresh = viewModel::load)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RocketLaunchScreen(
    uiState: RocketLaunchUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val pullToRefreshState = rememberPullToRefreshState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val collapsedFraction by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val titleFontSize by remember {
        derivedStateOf {
            lerp(32.sp.value, 20.sp.value, collapsedFraction).sp
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        "SpaceX Launches",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = titleFontSize
                    )
                },
                actions = {
                    TextButton(
                        enabled = uiState !is RocketLaunchUiState.Loading,
                        onClick = {
                            coroutineScope.launch {
                                onRefresh()
                            }
                        }) {
                        Text("Reload", fontWeight = FontWeight.SemiBold)
                    }
                }
            )
        }
    ) { padding ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = pullToRefreshState,
            isRefreshing = uiState is RocketLaunchUiState.Loading,
            onRefresh = {
                coroutineScope.launch {
                    onRefresh()
                }
            }
        ) {
            when (val uiState = uiState) {
                RocketLaunchUiState.Loading -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Loading...", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                is RocketLaunchUiState.Success -> {
                    LazyColumn {
                        items(
                            items = uiState.launches
                        ) { launch ->
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "${launch.missionName} - ${launch.launchYear}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = if (launch.launchSuccess == true) "Successful" else "Unsuccessful",
                                    // color = if (launch.launchSuccess == true) app_theme_successful else app_theme_unsuccessful,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(Modifier.height(8.dp))
                                val details = launch.details
                                if (!details.isNullOrBlank()) {
                                    Text(details, fontWeight = FontWeight.Medium)
                                }
                            }
                            HorizontalDivider(color = Color.LightGray)
                        }
                    }
                }

                is RocketLaunchUiState.Fail -> {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (!uiState.message.isNullOrBlank()) Text(uiState.message!!)
                        else Text("Something went wrong. Try again.")
                    }
                }
            }
        }
    }
}