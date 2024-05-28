package com.javimay.rickmortyapp.ui.character

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.javimay.rickmortyapp.domain.usecases.GetCharacterByIdUseCase
import com.javimay.rickmortyapp.domain.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterFragmentViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel(), LifecycleObserver {

    val loading = MutableLiveData<Boolean>()

    fun getCharacter(characterId: Long) = liveData {
        loading.postValue(true)
        emit(getCharacterByIdUseCase.execute(characterId))
    }
}