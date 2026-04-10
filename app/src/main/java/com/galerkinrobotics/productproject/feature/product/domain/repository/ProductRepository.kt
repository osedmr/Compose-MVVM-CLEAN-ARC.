package com.galerkinrobotics.productproject.feature.product.domain.repository

import com.galerkinrobotics.productproject.feature.product.domain.model.Product

interface ProductRepository {
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun getAllProducts(): List<Product>

    suspend fun getProductById(id: Int): Product?
}
