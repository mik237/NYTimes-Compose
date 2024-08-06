package me.ibrahim.nytimes

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.presentation.enums.CellsLayout
import me.ibrahim.nytimes.presentation.pages.TopStoriesView
import me.ibrahim.nytimes.presentation.pages.TopStoryDetailView
import me.ibrahim.nytimes.ui.theme.NYTimesTheme

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NYTimesTheme {

                var cellsLayout by rememberSaveable { mutableStateOf(CellsLayout.LIST) }
                val navigator = rememberListDetailPaneScaffoldNavigator<TopStory>()

                BackHandler(navigator.canNavigateBack()) {
                    navigator.navigateBack()
                }

                ListDetailPaneScaffold(
                    listPane = {
                        AnimatedPane(modifier = Modifier) {
                            TopStoriesView(
                                cellsLayout = cellsLayout,
                                onLayoutChange = { cellsLayout = it },
                                onClick = { story ->
                                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, story)
                                })
                        }
                    },
                    detailPane = {
                        AnimatedPane(modifier = Modifier) {
                            TopStoryDetailView { navigator.navigateBack() }
                        }
                    },
                    directive = navigator.scaffoldDirective,
                    value = navigator.scaffoldValue
                )

            }
        }
    }
}


@Parcelize
data class Car(val id: Int, val name: String = "My Car") : Parcelable

