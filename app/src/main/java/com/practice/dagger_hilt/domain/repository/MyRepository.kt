package com.practice.dagger_hilt.domain.repository

interface MyRepository {

    suspend fun doNetworkCall()
}