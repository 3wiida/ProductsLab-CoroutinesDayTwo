package com.ewida.mvvmlab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val id: Int,
    val availabilityStatus: String,
    val brand: String?,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val minimumOrderQuantity: Int,
    val price: Double,
    val rating: Double,
    val returnPolicy: String,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val thumbnail: String,
    val title: String,
    val warrantyInformation: String,
    val weight: Int
)