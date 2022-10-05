package com.suzume.weather.di

import com.suzume.weather.presentation.BaseCommunication
import com.suzume.weather.presentation.BaseManageResources
import com.suzume.weather.presentation.Communication
import com.suzume.weather.presentation.ManageResources
import dagger.Binds
import dagger.Module

@Module
interface PresentationModule{

    @Binds
    fun bindCommunication(impl: BaseCommunication): Communication

    @Binds
    fun bindManageResources(impl: BaseManageResources): ManageResources

}
