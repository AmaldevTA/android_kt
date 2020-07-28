package com.aml.pagging3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.aml.pagging3.database.AppDb
import com.aml.pagging3.model.Product
import com.aml.pagging3.source.AdvancedPagingSource
import com.aml.pagging3.source.MyPagingSource
import kotlinx.coroutines.flow.map

class MainViewModel(app: Application) : AndroidViewModel(app) {

    val dao = AppDb.get(app).productDao()


    val productPagingFlow = Pager(PagingConfig(pageSize = 10)) {
        MyPagingSource()
    }.flow.cachedIn(viewModelScope)


    val productPagingFlowFiltered = Pager(PagingConfig(pageSize = 10)) {
        MyPagingSource()
    }.flow.map { pagingData -> pagingData.filter { product -> product.index % 2 == 0 } }
        .cachedIn(viewModelScope)


    val productPagingFlowDb = Pager(PagingConfig(pageSize = 10)) {
        dao.getAll()
    }.flow.cachedIn(viewModelScope)


    @ExperimentalPagingApi
    val advancedProductPagingFlow = Pager(
        config = PagingConfig(pageSize = 50),
        remoteMediator = AdvancedPagingSource(AppDb.get(app))
    ) {
        dao.getAll()
    }.flow.cachedIn(viewModelScope)


    fun insert(product: Product) = ioThread {
        dao.insert(product)
    }
}