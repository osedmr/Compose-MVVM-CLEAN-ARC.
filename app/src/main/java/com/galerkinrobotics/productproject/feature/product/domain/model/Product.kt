package com.galerkinrobotics.productproject.feature.product.domain.model

data class Product(
    val id: Int = 0,
    val name: String,
    val icon: String,
    val image: String,
    /** Opsiyonel tanıtım videosu (yerel dosya yolu); liste kartında kullanılmaz. */
    val video: String = "",
    val description: String,
)
