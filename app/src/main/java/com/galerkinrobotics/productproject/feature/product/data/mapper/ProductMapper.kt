package com.galerkinrobotics.productproject.feature.product.data.mapper

import com.galerkinrobotics.productproject.feature.product.data.local.entity.ProductEntity
import com.galerkinrobotics.productproject.feature.product.domain.model.Product

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    name = name,
    icon = icon,
    image = image,
    video = video,
    description = description,
)

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    name = name,
    icon = icon,
    image = image,
    video = video,
    description = description,
)
