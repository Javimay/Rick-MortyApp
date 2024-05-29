package com.javimay.rickmortyapp.domain.usecases

import com.javimay.rickmortyapp.data.db.entities.Location
import com.javimay.rickmortyapp.domain.repository.ILocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: ILocationRepository
){
    suspend fun execute(): List<Location> = locationRepository.getLocations()
}