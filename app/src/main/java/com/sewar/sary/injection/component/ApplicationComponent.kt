package com.sewar.sary.injection.component

import android.content.Context
import com.sewar.sary.data.DataRepository
import com.sewar.sary.injection.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun context(): Context

    fun dataRepository(): DataRepository
}
