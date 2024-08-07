package me.ibrahim.nytimes.presentation.composable_views

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.ui.theme.NYTimesTheme
import me.ibrahim.nytimes.utils.Dimens.ExrtaSmallPadding
import me.ibrahim.nytimes.utils.Dimens.StoryCardSize

@Composable
fun StoryListItem(story: TopStory, onClick: (TopStory) -> Unit) {
    val context = LocalContext.current
    Card(
        onClick = { onClick.invoke(story) },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 1.dp,
            pressedElevation = 2.dp
        ),
        shape = RoundedCornerShape(5),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .padding(ExrtaSmallPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(StoryCardSize)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context).data(story.smallImageUrl).build(),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .height(StoryCardSize)
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = story.title ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = story.byline ?: "",
                    style = MaterialTheme.typography.labelMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StoryListItemPreview() {
    NYTimesTheme {
        StoryListItem(
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