package me.ibrahim.nytimes.presentation.pages

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.presentation.composable_views.ErrorView
import me.ibrahim.nytimes.presentation.composable_views.LoadingView
import me.ibrahim.nytimes.presentation.composable_views.NYTopAppBar
import me.ibrahim.nytimes.presentation.composable_views.TopStoriesGridView
import me.ibrahim.nytimes.presentation.composable_views.TopStoriesListView
import me.ibrahim.nytimes.presentation.enums.CellsLayout
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.presentation.viewmodels.UiState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopStoriesView(
    cellsLayout: CellsLayout,
    onLayoutChange: (CellsLayout) -> Unit,
    onClick: (TopStory) -> Unit
) {

    val nyTimesViewModel: NYTimesViewModel = viewModel(LocalContext.current as ComponentActivity)
    val topStoriesUiState by nyTimesViewModel.topStoriesUiState.collectAsStateWithLifecycle()

    val isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh = { nyTimesViewModel.fetchTopStories() })

    Scaffold(topBar = {
        NYTopAppBar(cellsLayout = cellsLayout, changeLayout = onLayoutChange)
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {

            when (topStoriesUiState) {
                is UiState.Error -> {/*show error UI*/
                    ErrorView(error = (topStoriesUiState as UiState.Error).error)
                }

                UiState.Loading -> {/*show loading ui*/
                    LoadingView()
                }

                is UiState.Success -> {/*show data as list or grid*/
                    when (cellsLayout) {
                        CellsLayout.GRID -> {
                            TopStoriesGridView((topStoriesUiState as UiState.Success).data) { story ->
                                nyTimesViewModel.setSelectedStory(story)
                                onClick(story)
                            }
                        }

                        CellsLayout.LIST -> {
                            TopStoriesListView((topStoriesUiState as UiState.Success).data) { story ->
                                nyTimesViewModel.setSelectedStory(story)
                                onClick(story)
                            }
                        }
                    }
                }

                else -> {/*Default do nothing*/
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}