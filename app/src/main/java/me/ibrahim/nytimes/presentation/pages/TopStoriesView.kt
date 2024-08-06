package me.ibrahim.nytimes.presentation.pages

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun TopStoriesView(
    cellsLayout: CellsLayout,
    onLayoutChange: (CellsLayout) -> Unit,
    onClick: (TopStory) -> Unit
) {

    val nyTimesViewModel: NYTimesViewModel = viewModel(LocalContext.current as ComponentActivity)
    val topStoriesUiState by nyTimesViewModel.topStoriesUiState.collectAsState()

    Scaffold(topBar = {
        NYTopAppBar(cellsLayout = cellsLayout, changeLayout = onLayoutChange)
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
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
                            TopStoriesGridView((topStoriesUiState as UiState.Success).data, onClick = onClick)
                        }

                        CellsLayout.LIST -> {
                            TopStoriesListView((topStoriesUiState as UiState.Success).data, onClick = onClick)
                        }
                    }
                }

                else -> {/*Default do nothing*/
                }
            }
        }
    }
}