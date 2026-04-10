package com.galerkinrobotics.productproject.feature.product.presentation.add

data class ProductAddUiState(
    val name: String = "",
    val description: String = "",
    /** Seçilen ikon görseli (content URI veya yol) */
    val iconUri: String = "",
    val imageUri: String = "",
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
)
