package com.rodrip.marketya.productList.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    filtersVisible: Boolean = true,
    onFilterSelected: (Boolean) -> Unit = {},
    settingVisible: Boolean = true,
    onSettingSelected: () -> Unit = {}
) {

    TopAppBar(
        title = {
            Text(
                text = "MarketYa",
                style = MaterialTheme.typography.titleLarge, fontWeight = Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        actions = {
            IconButton(
                onClick = { onFilterSelected(!filtersVisible) },
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = if (filtersVisible) "Ocultar filtros" else "Mostrar filtros",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            IconButton(
                onClick = { onSettingSelected() },
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = if (settingVisible) "Ocultar configuración" else "Mostrar configuración",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    )
}