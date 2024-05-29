package com.javimay.rickmortyapp.domain.usecases

import com.javimay.rickmortyapp.domain.repository.IEpisodeRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val episodeRepository: IEpisodeRepository
) {
    suspend fun execute() = episodeRepository.getEpisodes().toMutableList()
}