package com.practice.dagger_hilt.data.repository

import android.app.Application
import com.practice.dagger_hilt.R
import com.practice.dagger_hilt.data.remote.MyApi
import com.practice.dagger_hilt.domain.repository.MyRepository

class MyRepositoryImpl(
    private val api: MyApi,
    private val appContext: Application
): MyRepository {

    init {
        val appName = appContext.getString(R.string.app_name)
        println("app name : $appName")
    }

    override suspend fun doNetworkCall() {

    }
}