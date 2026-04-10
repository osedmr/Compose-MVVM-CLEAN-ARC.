package com.galerkinrobotics.productproject.feature.product.presentation.detail

import com.galerkinrobotics.productproject.feature.product.domain.model.Product

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Loaded(val product: Product) : ProductDetailUiState
    data object NotFound : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}
