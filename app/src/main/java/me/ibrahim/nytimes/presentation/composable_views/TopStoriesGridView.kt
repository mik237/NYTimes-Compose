package me.ibrahim.nytimes.presentation.composable_views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.utils.Dimens.ExrtaSmallPadding2
import me.ibrahim.nytimes.utils.Dimens.MediumPadding1

@Composable
fun TopStoriesGridView(topStories: List<TopStory>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(MediumPadding1),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExrtaSmallPadding2)
    ) {

        items(items = topStories) { story ->
            StoryGridItem(story = story) {}
        }

    }
}