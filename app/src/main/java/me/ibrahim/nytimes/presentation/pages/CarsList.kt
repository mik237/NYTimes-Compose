package me.ibrahim.nytimes.presentation.pages

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ibrahim.nytimes.Car
import me.ibrahim.nytimes.presentation.composable_views.NYTopAppBar
import me.ibrahim.nytimes.presentation.enums.CellsLayout
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.presentation.viewmodels.UiState
import me.ibrahim.nytimes.ui.theme.NYTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsList(
    layout: CellsLayout,
    onLayoutChange: (CellsLayout) -> Unit,
    onClick: (Car) -> Unit
) {
    val cellsLayout by remember { mutableStateOf(layout) }


    val nyTimesViewModel: NYTimesViewModel = viewModel(LocalContext.current as ComponentActivity)
    val topStoriesUiState by nyTimesViewModel.topStoriesUiState.collectAsState()

    Scaffold(topBar = {
        NYTopAppBar(cellsLayout = layout, changeLayout = onLayoutChange)
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .statusBarsPadding()
        ) {


            when (topStoriesUiState) {
                is UiState.Default -> {

                }

                is UiState.Error -> {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "Error from top stories")
                    }
                }

                is UiState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    when (cellsLayout) {
                        CellsLayout.GRID -> {
                            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                                itemsIndexed(items = (topStoriesUiState as UiState.Success).data) { index, item ->
                                    GridCarItem(index = index + 1, item.byline ?: "author name") { car ->
                                        onClick.invoke(car)
//                                        nyTimesViewModel.setSelectedCar(car)
                                    }
                                }
                            }
                        }

                        CellsLayout.LIST -> {
                            LazyColumn {
                                itemsIndexed(items = (topStoriesUiState as UiState.Success).data) { index, item ->
                                    ListCarItem(index = index + 1, item.byline ?: "author name") { car ->
//                                        nyTimesViewModel.setSelectedCar(car)
                                        onClick.invoke(car)
                                    }
                                }
                            }
                        }
                    }
                }
            }


        }
    }
    DisposableEffect(key1 = true) {
        onDispose { Log.d("Disposable", "CarsList: Disposed") }
    }
}


@Composable
fun ListCarItem(index: Int, title: String, onClick: (Car) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick.invoke(Car(index)) }
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Red), shape = RoundedCornerShape(size = 5.dp)
            )
            .padding(vertical = 5.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(horizontal = 5.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun GridCarItem(index: Int, title: String, onClick: (Car) -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick.invoke(Car(index)) }
            .padding(vertical = 5.dp),
    ) {
        val shape = CircleShape
        Text(
            text = title,
            style = TextStyle(
                color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
                .border(2.dp, MaterialTheme.colorScheme.secondary, shape)
                .background(MaterialTheme.colorScheme.primary, shape)
                .padding(16.dp)
        )
    }
}



@Preview
@Composable
private fun CarsListPreview() {
    NYTimesTheme {
        GridCarItem(2, "title") {}
    }
}