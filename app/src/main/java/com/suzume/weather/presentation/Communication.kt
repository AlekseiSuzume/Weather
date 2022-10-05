package com.suzume.weather.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import javax.inject.Inject

interface Communication {

    fun updateData(state: State)

    fun observe(owner: LifecycleOwner, observer: Observer<State>)

}

class BaseCommunication @Inject constructor() : Communication {

    private val liveData = MutableLiveData<State>()

    override fun updateData(state: State) {
        liveData.value = state
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<State>) {
        liveData.observe(owner, observer)
    }

}