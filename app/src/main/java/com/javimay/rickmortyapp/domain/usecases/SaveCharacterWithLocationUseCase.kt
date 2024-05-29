package com.javimay.rickmortyapp.domain.usecases

import com.javimay.rickmortyapp.data.model.relations.CharacterLocationCrossRef
import com.javimay.rickmortyapp.domain.repository.ICharacterRepository
import javax.inject.Inject

class SaveCharacterWithLocationUseCase @Inject constructor(
    private val characterRepository: ICharacterRepository,
) {
    suspend fun execute(characterWithLocationList: List<CharacterLocationCrossRef>) {
        characterRepository.saveCharactersWithLocationsList(characterWithLocationList)
    }
}