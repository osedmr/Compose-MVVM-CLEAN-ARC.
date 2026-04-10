package com.galerkinrobotics.productproject.feature.product.presentation.navigation

sealed class ProductRoute(val route: String) {
    data object List : ProductRoute("product_list")
    data object Add : ProductRoute("product_add")
    data object Detail : ProductRoute("product_detail/{${ProductNavArgs.PRODUCT_ID}}") {
        fun createRoute(productId: Int): String = "product_detail/$productId"
    }
}
