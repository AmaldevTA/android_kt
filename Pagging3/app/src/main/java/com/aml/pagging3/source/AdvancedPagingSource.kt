package com.aml.pagging3.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.aml.pagging3.database.AppDb
import com.aml.pagging3.ioThread
import com.aml.pagging3.model.Product
import com.aml.pagging3.network.NetworkService
import java.io.IOException

@ExperimentalPagingApi
class AdvancedPagingSource(database: AppDb) : RemoteMediator<Int, Product>() {

    private val dao = database.productDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Product>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    lastItem.index
                }
            }

            val query = if(loadKey == null){
                1
            }else{
                loadKey + 1
            }
            val response = NetworkService().getNextDataSet(query)

            ioThread {
                dao.insertAll(response)
            }


            MediatorResult.Success(
                endOfPaginationReached = response.isNullOrEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }
}