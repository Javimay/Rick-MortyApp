package com.javimay.rickmortyapp.ui.home

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.domain.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel(), LifecycleObserver {

    fun getCharacter() = liveData {
        emit(getCharacterUseCase.execute())
    }
}