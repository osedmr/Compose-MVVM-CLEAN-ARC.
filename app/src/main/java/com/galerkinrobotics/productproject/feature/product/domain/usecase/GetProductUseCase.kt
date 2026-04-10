package com.galerkinrobotics.productproject.feature.product.domain.usecase

import com.galerkinrobotics.productproject.feature.product.domain.model.Product
import com.galerkinrobotics.productproject.feature.product.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke(): List<Product> = productRepository.getAllProducts()
}
