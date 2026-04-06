package com.rodrip.marketya.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.rodrip.marketya.productList.presentation.ProductListScreen

@Composable
fun NavGraph() {
    val backStack = rememberNavBackStack(Screen.ProductList)
    val entries = entryProvider<NavKey> {
        entry<Screen.ProductList> {
            //aca van cada una de las vistas
            ProductListScreen()
        }
        entry<Screen.ProductDetail> {
            Text(text = "Product Detail", fontSize = 80.sp)

        }
        entry<Screen.Cart> {
            Text(text = "Carrito", fontSize = 80.sp)

        }
        entry<Screen.Setting> {
            Text(text = "Setting", fontSize = 80.sp)

        }

    }

    NavDisplay(
        backStack = backStack,
        entryProvider = entries,
        onBack = {
            backStack.removeLastOrNull()
        }
    )

}

