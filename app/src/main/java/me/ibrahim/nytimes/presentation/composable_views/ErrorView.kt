package me.ibrahim.nytimes.presentation.composable_views

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ibrahim.nytimes.R
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesEvents
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel
import me.ibrahim.nytimes.ui.theme.NYTimesTheme

@Composable
fun ErrorView(
    @DrawableRes imageId: Int = R.drawable.icon_data_not_found,
    @StringRes errorMsg: Int = R.string.error_msg,
    errorDesc: String = ""
) {

    val nyTimesViewModel: NYTimesViewModel = viewModel(LocalContext.current as ComponentActivity)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = null,
                modifier = Modifier
                    .padding(12.dp)
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = stringResource(id = errorMsg),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(0.dp, 9.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = errorDesc,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )


            TextButton(onClick = {
                nyTimesViewModel.onEvent(NYTimesEvents.FetchStories)
            }) {
                Text(text = stringResource(id = R.string.txt_try_again))
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ErrorViewPreview() {
    NYTimesTheme {
        ErrorView(
            errorMsg = R.string.error_msg,
            imageId = R.drawable.icon_data_not_found,
            errorDesc = "Connection Error: Please check your internet connection!"
        )
    }
}