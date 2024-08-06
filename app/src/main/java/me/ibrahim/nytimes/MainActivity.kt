package me.ibrahim.nytimes

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import me.ibrahim.nytimes.presentation.pages.CarDetail
import me.ibrahim.nytimes.presentation.pages.CarsList
import me.ibrahim.nytimes.presentation.pages.CellsLayout
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.ui.theme.NYTimesTheme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val nyTimesViewModel: NYTimesViewModel by viewModels<NYTimesViewModel>()

        setContent {
            NYTimesTheme {

                var cellsLayout by rememberSaveable { mutableStateOf(CellsLayout.GRID) }
                val navigator = rememberListDetailPaneScaffoldNavigator<Car>()

                BackHandler(navigator.canNavigateBack()) {
                    navigator.navigateBack()
                }
                nyTimesViewModel.fetchTopStories()
                Surface {
                    ListDetailPaneScaffold(
                        listPane = {
                            AnimatedPane(modifier = Modifier) {
                                CarsList(
                                    layout = cellsLayout,
                                    onLayoutChange = { cellsLayout = it },
                                    onClick = {
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it)
                                    }
                                )
                            }
                        },
                        detailPane = {
                            AnimatedPane(modifier = Modifier) {
                                navigator.currentDestination?.content?.let {
                                    CarDetail(car = Car(id = -111, name = "Dummy Car ${it.id}")) {
                                        navigator.navigateBack()
                                    }
                                }
                            }
                        },
                        directive = navigator.scaffoldDirective,
                        value = navigator.scaffoldValue
                    )
                }
            }
        }
    }
}


@Parcelize
data class Car(val id: Int, val name: String = "My Car") : Parcelable

