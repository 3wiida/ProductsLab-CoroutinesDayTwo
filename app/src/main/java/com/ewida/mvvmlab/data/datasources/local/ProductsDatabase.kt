package com.ewida.mvvmlab.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ewida.mvvmlab.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductsDatabase : RoomDatabase() {

    abstract fun getDao(): ProductsDao

    companion object {
        @Volatile
        private var instance: ProductsDatabase? = null
        private const val DB_NAME = "PRODUCTS_DATABASE"

        fun getInstance(context: Context): ProductsDatabase {
            return instance ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context,
                    ProductsDatabase::class.java,
                    DB_NAME
                ).build()
                instance = db
                db
            }
        }
    }
}