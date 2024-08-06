package me.ibrahim.nytimes.presentation.pages

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ibrahim.nytimes.Car
import me.ibrahim.nytimes.MySharedViewModel
import me.ibrahim.nytimes.ui.theme.NYTimesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetail(car: Car, navigateUp: () -> Unit) {

    val orientation = LocalConfiguration.current.orientation
    val sharedViewModel: MySharedViewModel = viewModel(LocalContext.current as ComponentActivity)
    val selectedCar by sharedViewModel.selectedCar.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "${selectedCar?.name} ${selectedCar?.id}") },
                navigationIcon = {
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        IconButton(onClick = { navigateUp.invoke() }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                })

        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = it.calculateTopPadding(), horizontal = 20.dp)
                .statusBarsPadding()
                .background(color = Color.White)
                .border(
                    border = BorderStroke(width = 1.dp, color = Color.Red),
                    shape = RoundedCornerShape(size = 10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(vertical = 20.dp)) {
                Text(
                    modifier = Modifier.padding(vertical = 20.dp),
                    text = "Car #${selectedCar?.id}",
                    style = MaterialTheme.typography.displaySmall
                )
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    ElevatedButton(onClick = navigateUp) {
                        Text(text = "Click to Back")
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun CarDetailPreview() {
    NYTimesTheme {
        CarDetail(car = Car(id = 3)) {}
    }
}