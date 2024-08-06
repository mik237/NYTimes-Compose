package me.ibrahim.nytimes.presentation.composable_views

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.utils.Dimens.ExrtaSmallPadding2
import me.ibrahim.nytimes.utils.Dimens.MediumPadding1

@Composable
fun TopStoriesListView(
    topStories: List<TopStory>,
    onClick: (TopStory) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExrtaSmallPadding2)
    ) {
        items(items = topStories) { story ->
            StoryListItem(story = story, onClick = onClick)
        }
    }
}