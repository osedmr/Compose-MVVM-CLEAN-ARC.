package com.galerkinrobotics.productproject.feature.product.presentation.list

import com.galerkinrobotics.productproject.feature.product.domain.model.Product

sealed interface ProductListUiState {
    data object Loading : ProductListUiState
    data class Success(val products: List<Product>) : ProductListUiState
    data class Error(val message: String) : ProductListUiState
}