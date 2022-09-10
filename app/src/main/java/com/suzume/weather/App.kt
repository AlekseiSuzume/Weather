package com.suzume.weather

import android.app.Application
import com.suzume.weather.di.DaggerApplicationComponent

class App : Application() {

    val companion by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}