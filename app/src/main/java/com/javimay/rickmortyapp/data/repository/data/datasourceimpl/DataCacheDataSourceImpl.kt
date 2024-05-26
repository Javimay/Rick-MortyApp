package com.javimay.rickmortyapp.data.repository.data.datasourceimpl

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.repository.data.datasource.IDataCacheDataSource
import javax.inject.Inject

class DataCacheDataSourceImpl @Inject constructor() : IDataCacheDataSource {

    private var data = Data()

    override suspend fun getDataFromCache(): Data = data

    override suspend fun saveDataToCache(data: Data) {
        this.data = data
    }

}