package com.galerkinrobotics.productproject.di

import com.galerkinrobotics.productproject.feature.product.data.repository.ProductRepositoryImpl
import com.galerkinrobotics.productproject.feature.product.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl,
    ): ProductRepository
}
