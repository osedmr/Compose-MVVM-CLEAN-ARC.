package com.galerkinrobotics.productproject.feature.product.data.repository

import com.galerkinrobotics.productproject.feature.product.data.datasource.ProductDataSource
import com.galerkinrobotics.productproject.feature.product.data.local.storage.ProductMediaStore
import com.galerkinrobotics.productproject.feature.product.data.mapper.toDomain
import com.galerkinrobotics.productproject.feature.product.data.mapper.toEntity
import com.galerkinrobotics.productproject.feature.product.domain.model.Product
import com.galerkinrobotics.productproject.feature.product.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val productMediaStore: ProductMediaStore,
) : ProductRepository {

    override suspend fun insertProduct(product: Product) {
        val stored = product.copy(
            icon = productMediaStore.persistUriString(product.icon),
            image = productMediaStore.persistUriString(product.image),
        )
        productDataSource.insertProduct(stored.toEntity())
    }

    override suspend fun deleteProduct(product: Product) {
        productDataSource.deleteProduct(product.toEntity())
    }

    override suspend fun updateProduct(product: Product) {
        val stored = product.copy(
            icon = productMediaStore.persistUriString(product.icon),
            image = productMediaStore.persistUriString(product.image),
        )
        productDataSource.updateProduct(stored.toEntity())
    }

    override suspend fun getAllProducts(): List<Product> {
        return productDataSource.getAllProducts().map { it.toDomain() }
    }

    override suspend fun getProductById(id: Int): Product? {
        return productDataSource.getProductById(id)?.toDomain()
    }
}
