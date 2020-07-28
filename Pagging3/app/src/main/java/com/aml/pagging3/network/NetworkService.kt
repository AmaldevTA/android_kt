package com.aml.pagging3.network

import com.aml.pagging3.model.Product
import kotlinx.coroutines.delay

class NetworkService {
    suspend fun getNextDataSet(index: Int): List<Product> {
        delay(1000)
        val productList = mutableListOf<Product>()
        for (i in index..index + 49) {
            productList.add(Product(i))
        }
        return productList
    }
}