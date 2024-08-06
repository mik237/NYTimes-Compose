package me.ibrahim.nytimes.presentation.composable_views

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import me.ibrahim.nytimes.R
import me.ibrahim.nytimes.presentation.enums.CellsLayout
import me.ibrahim.nytimes.presentation.viewmodels.NYTimesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NYTopAppBar(
    cellsLayout: CellsLayout,
    changeLayout: (CellsLayout) -> Unit
) {

    val nyTimesViewModel: NYTimesViewModel = viewModel(LocalContext.current as ComponentActivity)
    val state by nyTimesViewModel.state.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }
    val filterApplied = state.topStories.toSet() != state.filteredStories.toSet()


    TopAppBar(title = {
        Box {
            TextField(modifier = Modifier.fillMaxWidth(), value = "", onValueChange = {})
        }
    }, actions = {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_filter),
                contentDescription = "Filter",
                tint = if (filterApplied) Color.Red else Color.Black
            )
        }

        IconButton(onClick = {
            val layout = if (cellsLayout == CellsLayout.LIST) CellsLayout.GRID else CellsLayout.LIST
            changeLayout(layout)
        }) {
            Icon(
                painter = painterResource(id = if (cellsLayout == CellsLayout.LIST) R.drawable.icon_grid else R.drawable.icon_list),
                contentDescription = null
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            state.sections.forEach { option ->
                DropdownMenuItem(text = {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.titleLarge
                    )
                }, onClick = {
                    nyTimesViewModel.filterTopStories(option)
                    expanded = false
                })
            }
        }
    })
}