package me.ibrahim.nytimes.presentation.composable_views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import me.ibrahim.nytimes.presentation.enums.CellsLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NYTopAppBar(
    cellsLayout: CellsLayout,
    changeLayout: (CellsLayout) -> Unit
) {

    val options = listOf("Option 1", "Option 2", "Option 3")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    TopAppBar(title = {
        Box {
            TextField(modifier = Modifier.fillMaxWidth(),
                value = "", onValueChange = {})
        } },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.Settings, contentDescription = "More Options")
            }

            IconButton(onClick = {
                val layout = if (cellsLayout == CellsLayout.LIST) CellsLayout.GRID else CellsLayout.LIST
                changeLayout(layout)
            }) {
                Icon(
                    imageVector = if (cellsLayout == CellsLayout.LIST) Icons.Default.Menu else Icons.Default.DateRange,
                    contentDescription = null
                )
            }




            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(text = { Text(text = option) }, onClick = {
                        selectedOption = option
                        expanded = false
                    })
                }
            }
        })
}