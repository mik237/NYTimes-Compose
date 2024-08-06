package me.ibrahim.nytimes.presentation.composable_views

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.ui.theme.NYTimesTheme
import me.ibrahim.nytimes.utils.Dimens.ExrtaSmallPadding
import me.ibrahim.nytimes.utils.Dimens.StoryCardSize

@Composable
fun StoryGridItem(story: TopStory, onClick: (TopStory) -> Unit) {
    val context = LocalContext.current

    Card(
        onClick = { onClick.invoke(story) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 5.dp,
            pressedElevation = 7.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(ExrtaSmallPadding)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.primary)
                .clickable { onClick.invoke(story) },
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context).data(story.smallImageUrl).build(),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .height(StoryCardSize)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = story.title ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = story.byline ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoryGridItemPreview() {
    NYTimesTheme {
        StoryGridItem(
            story = TopStory(
                title = "‘House of the Dragon’: 5 Questions as We Look Ahead to Season 3",
                description = "result.abstract",
                byline = "By Jennifer Vineyard",
                createdDate = "result.createdDate",
                itemType = "result.itemType",
                caption = "result.multimedia?.get(0)?.caption" ?: "",
                largeImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-superJumbo.jpg",
                smallImageUrl = "https://static01.nyt.com/images/2024/08/05/arts/05dragon-questions/05dragon-questions-thumbLarge.jpg",
                publishedDate = "result.publishedDate",
                section = "result.section",
                shortUrl = "result.shortUrl",
                subsection = "result.subsection",
                updatedDate = "result.updatedDate",
                url = "result.url"
            )
        ) {

        }
    }
}