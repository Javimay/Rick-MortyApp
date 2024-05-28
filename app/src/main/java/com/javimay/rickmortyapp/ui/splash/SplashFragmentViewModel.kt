package com.javimay.rickmortyapp.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.widget.ImageView
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.javimay.rickmortyapp.data.db.entities.Character
import com.javimay.rickmortyapp.domain.usecases.GetDataUseCase
import com.javimay.rickmortyapp.utils.SECOND
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashFragmentViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) : ViewModel(), LifecycleObserver {

    private val durationInSeconds = 4
    private val appearAlpha = 1f
    var animFinished = MutableLiveData<Boolean>()
    var dataDownloaded = MutableLiveData<Boolean>()

    fun logoAppear(view: ImageView) {
        view.animate()
            .alpha(appearAlpha)
            .setDuration(SECOND * durationInSeconds)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animFinished.postValue(true)
                }
            })
            .start()
    }

    fun downloadData() = liveData {
        emit(getDataUseCase.execute())
    }

}