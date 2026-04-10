package com.galerkinrobotics.productproject.feature.product.data.datasource

import com.galerkinrobotics.productproject.feature.product.data.local.dao.ProductDao
import com.galerkinrobotics.productproject.feature.product.data.local.entity.ProductEntity
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val productDao: ProductDao,
) {
    suspend fun insertProduct(product: ProductEntity) {
        productDao.insert(product)
    }

    suspend fun deleteProduct(product: ProductEntity) {
        productDao.delete(product)
    }

    suspend fun updateProduct(product: ProductEntity) {
        productDao.update(product)
    }

    suspend fun getAllProducts(): List<ProductEntity> = productDao.getAllProducts()

    suspend fun getProductById(id: Int): ProductEntity? = productDao.getProductById(id)
}
