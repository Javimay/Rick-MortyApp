package com.javimay.rickmortyapp.data.repository.data.datasource

import com.javimay.rickmortyapp.data.model.Data

interface IDataCacheDataSource {
    suspend fun getDataFromCache(): Data

    suspend fun saveDataToCache(data: Data)
}