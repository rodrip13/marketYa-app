package com.rodrip.marketya.productList.presentation

sealed interface ProductListEvent {
    data class ShowMessage(val message: String) : ProductListEvent
}