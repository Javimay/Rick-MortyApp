package com.javimay.rickmortyapp.domain.usecases

import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
private val characterRepository: ICharacterRepository) {
    suspend fun execute(characterId: Long): Character {
        return characterRepository.getCharacterById(characterId)
    }
}