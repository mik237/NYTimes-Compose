package me.ibrahim.nytimes.presentation.composable_views

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesEvents
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.utils.Dimens.ExrtaSmallPadding2
import me.ibrahim.nytimes.utils.Dimens.MediumPadding1

@OptIn(FlowPreview::class)
@Composable
fun TopStoriesListView(
    topStories: List<TopStory>,
    onClick: (TopStory) -> Unit
) {

    val nyTimesViewModel: NYTimesViewModel = viewModel(LocalContext.current as ComponentActivity)
    val scrollPosition by nyTimesViewModel.scrollPosition.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = scrollPosition)

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .debounce(300L)
            .distinctUntilChanged()
            .collectLatest {
                nyTimesViewModel.onEvent(NYTimesEvents.ScrollPosition(position = it))
            }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExrtaSmallPadding2),
        state = scrollState
    ) {
        items(items = topStories, key = { it }) { story ->
            StoryListItem(story = story, onClick = onClick)
        }
    }
}