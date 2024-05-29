package com.javimay.rickmortyapp.ui.character

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.javimay.rickmortyapp.domain.usecases.GetCharacterByIdUseCase
import com.javimay.rickmortyapp.domain.usecases.GetLocationsByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterFragmentViewModel @Inject constructor(
    private val getLocationsByIdsUseCase: GetLocationsByIdsUseCase
) : ViewModel(), LifecycleObserver {

    val loading = MutableLiveData<Boolean>()

    fun getCharacterLocations(locationsIds: List<Long>) = liveData {
        loading.postValue(true)
        emit(getLocationsByIdsUseCase.execute(locationsIds.map { it.toInt() }))
    }
}