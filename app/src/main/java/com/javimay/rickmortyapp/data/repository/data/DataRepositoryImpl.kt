package com.javimay.rickmortyapp.data.repository.data

import android.util.Log
import com.javimay.rickmortyapp.data.model.Data
import com.javimay.rickmortyapp.data.repository.data.datasource.IDataRemoteDataSource
import com.javimay.rickmortyapp.domain.repository.IDataRepository
import com.javimay.rickmortyapp.utils.DataType
import retrofit2.Response
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val dataRemoteDataSource: IDataRemoteDataSource
) : IDataRepository {

    companion object {
        val TAG = DataRepositoryImpl::class.simpleName;
    }

    override suspend fun getData(dataType: DataType, page: Int?): Data {
        return getDataFromApi(dataType, page)
    }

    private suspend fun getDataFromApi(dataType: DataType, page: Int?): Data {
        lateinit var data: Data
        try {
            val response: Response<Data> =
                if (page == null) {
                    when (dataType) {
                        DataType.Character -> dataRemoteDataSource.getDataCharacters()
                        DataType.Episode -> dataRemoteDataSource.getDataEpisodes()
                        DataType.Location -> dataRemoteDataSource.getDataLocations()
                    }
                } else {
                    when (dataType) {
                        DataType.Character -> dataRemoteDataSource.getDataCharactersByPage(page)
                        DataType.Episode -> dataRemoteDataSource.getDataEpisodesByPage(page)
                        DataType.Location -> dataRemoteDataSource.getDataLocationsByPage(page)
                    }
                }

            val body = response.body()
            if (body != null) {
                data = body
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return data
    }
}

