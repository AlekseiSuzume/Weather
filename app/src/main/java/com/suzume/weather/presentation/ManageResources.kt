package com.suzume.weather.presentation

import android.app.Application
import androidx.annotation.StringRes
import javax.inject.Inject

interface ManageResources {

    fun string(@StringRes id: Int): String

}

class BaseManageResources @Inject constructor(
    private val application: Application,
) : ManageResources {
    override fun string(id: Int): String = application.getString(id)
}