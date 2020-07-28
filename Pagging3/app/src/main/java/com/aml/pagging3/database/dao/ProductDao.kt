package com.aml.pagging3.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aml.pagging3.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    fun getAll(): PagingSource<Int, Product>

    @Insert
    fun insert(product: Product)

    @Insert
    fun insertAll(product: List<Product>)
}