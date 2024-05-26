package com.javimay.rickmortyapp.domain.repository

import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.utils.DataType


interface IDataRepository {
    suspend fun getData(dataType: DataType, page: Int? = null): Data
}
