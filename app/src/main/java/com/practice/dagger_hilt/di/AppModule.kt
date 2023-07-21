package com.practice.dagger_hilt.di

import android.app.Application
import com.practice.dagger_hilt.data.remote.MyApi
import com.practice.dagger_hilt.data.repository.MyRepositoryImpl
import com.practice.dagger_hilt.domain.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // These functions are not directly called by developers!!!
    // they are only called by dagger hilt behind the scenes!!

    @Provides
    @Singleton
    // api retrofit instance
    fun provideMyApi(): MyApi {
        return Retrofit.Builder()
            .baseUrl("https://test.com")
            .build()
            .create(MyApi::class.java)
    }

    // how to create repository implementation
    @Provides
    @Singleton
    fun provideMyRepository(api: MyApi, appContext: Application, @Named("hello1") hello1: String): MyRepository {
        // return implementation
        return MyRepositoryImpl(api, appContext)

    }

    @Provides
    @Singleton
    @Named("hello1")
    fun provideString1() = "hello1"

    @Provides
    @Singleton
    @Named("hello2")
    fun provideString2() = "hello2"

}