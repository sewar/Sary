package com.sewar.sary.injection.module

import com.sewar.sary.data.SaryService
import com.sewar.sary.extensions.checkNotMainThread
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
private annotation class InternalApi

@Module
object DataModule {
    @InternalApi
    @Singleton
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        checkNotMainThread()
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                requestBuilder.header("Device-Type", "android")
                requestBuilder.header("App-Version", "3.1.1.0.0")
                //ar for Arabic, en for English
                requestBuilder.header("Accept-Language", "en")
                requestBuilder.header(
                    "Authorization",
                    "token eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ODg2NiwidXNlcl9waG9uZSI6Ijk2NjU2NDk4OTI1MCJ9.VYE28vtnMRLmwBISgvvnhOmPuGueW49ogOhXm5ZqsGU"
                )

                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Singleton
    @Provides
    internal fun provideSaryService(@InternalApi client: Lazy<OkHttpClient>): SaryService {
        return Retrofit.Builder()
            .callFactory { request -> client.get().newCall(request) }
            .baseUrl(SaryService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(SaryService::class.java)
    }
}
