package com.sewar.sary.injection.module

import android.content.Context
import com.sewar.sary.data.ApiDataRepository
import com.sewar.sary.data.DataRepository
import com.sewar.sary.data.FakeDataRepository
import com.sewar.sary.data.SaryService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provide application-level dependencies.
 */
@Module(includes = [DataModule::class])
object ApplicationModule {
    @Singleton
    @Provides
    internal fun provideDataRepository(
        context:Context,
        saryService: SaryService,
    ): DataRepository {
        return ApiDataRepository(saryService)
//        return FakeDataRepository(context)
    }
}
