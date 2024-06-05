package com.javimay.rickmortyapp.domain.usecases

import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import javax.inject.Inject

class GetCharactersByPageUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository
) {
    suspend fun execute() = characterRepository.getCharactersByPage()
}