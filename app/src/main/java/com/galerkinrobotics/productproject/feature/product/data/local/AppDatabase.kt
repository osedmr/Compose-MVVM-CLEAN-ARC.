package com.galerkinrobotics.productproject.feature.product.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galerkinrobotics.productproject.feature.product.data.local.dao.ProductDao
import com.galerkinrobotics.productproject.feature.product.data.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
