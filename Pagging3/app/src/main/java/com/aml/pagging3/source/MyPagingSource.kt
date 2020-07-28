package com.aml.pagging3.source

import androidx.paging.PagingSource
import com.aml.pagging3.model.Product
import com.aml.pagging3.network.NetworkService

class MyPagingSource : PagingSource<Int, Product>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val nextPageNumber = params.key ?: 1

            LoadResult.Page(
                data = NetworkService().getNextDataSet(nextPageNumber),
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 50
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}