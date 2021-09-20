package com.sewar.sary.injection.component

import com.sewar.sary.injection.PerActivity
import com.sewar.sary.injection.module.ActivityModule
import com.sewar.sary.ui.MainActivity
import com.sewar.sary.ui.MainFragment
import com.sewar.sary.ui.StoreFragment
import com.sewar.sary.ui.StoreViewModel
import dagger.Component

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {
    @Component.Factory
    interface Factory {
        fun create(applicationComponent: ApplicationComponent): ActivityComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(mainFragment: MainFragment)

    fun inject(storeFragment: StoreFragment)
    fun inject(storeViewModel: StoreViewModel)
}
