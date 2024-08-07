package me.ibrahim.nytimes.presentation.pages

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ibrahim.nytimes.domain.models.TopStory
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.ui.theme.NYTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopStoryDetailView(navigateBack: () -> Unit) {
    val orientation = LocalConfiguration.current.orientation
    val nyTimesViewModel: NYTimesViewModel = viewModel(LocalContext.current as ComponentActivity)
    val state by nyTimesViewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Top Story") }, navigationIcon = {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                IconButton(onClick = navigateBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
            }
        })
    }) { padding ->
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            state.selectedStory?.let { DetailViewColumnLayout(topStory = it, padding = padding) }
        } else {
            state.selectedStory?.let { DetailViewRowLayout(topStory = it, padding = padding) }
        }
    }
}

@Composable
fun DetailViewRowLayout(topStory: TopStory, padding: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val context = LocalContext.current
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(context).data(topStory.largeImageUrl).build(),
            contentDescription = null,
        )
        DetailViewText(topStory = topStory)
    }
}

@Composable
fun DetailViewColumnLayout(topStory: TopStory, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding), verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(context).data(topStory.largeImageUrl).build(),
            contentDescription = null,
        )
        DetailViewText(topStory = topStory)
    }
}

@Composable
fun DetailViewText(topStory: TopStory) {
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


@Preview
@Composable
private fun TopStoryDetailViewPreview() {
    NYTimesTheme {
        TopStoryDetailView {}
    }
}