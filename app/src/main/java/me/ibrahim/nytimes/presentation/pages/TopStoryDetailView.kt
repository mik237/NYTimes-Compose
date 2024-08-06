package me.ibrahim.nytimes.presentation.pages

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.ui.theme.NYTimesTheme
import me.ibrahim.nytimes.utils.Dimens.ExrtaSmallPadding
import me.ibrahim.nytimes.utils.Dimens.StoryCardSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopStoryDetailView(topStory: TopStory, navigateBack: () -> Unit) {

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Top Story") }, navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
                )
            }
        })
    }) {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context).data(topStory.largeImageUrl).build(),
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = topStory.title ?: "",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = topStory.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = topStory.byline ?: "",
                    style = MaterialTheme.typography.labelMedium
                        .copy(
                            fontWeight = FontWeight.Bold
                        ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
}


@Preview
@Composable
private fun TopStoryDetailViewPreview() {
    NYTimesTheme {
        TopStoryDetailView(
            topStory = TopStory(
                title = "‘House of the Dragon’: 5 Questions as We Look Ahead to Season 3",
                description = "Where is that wagon going? And who were those unfamiliar dragons? The Season 2 finale left viewers with many cliffhangers and much to ponder.",
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