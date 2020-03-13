package com.pedromunhoz.pokeapp.di

import com.pedromunhoz.data.LocalDataSource
import com.pedromunhoz.data.PokeRepository
import com.pedromunhoz.data.RemoteDataSource
import com.pedromunhoz.data_local.PokeDataSource
import com.pedromunhoz.data_remote.PokeRemoteDataSource
import com.pedromunhoz.data_remote.service.PokeWebServiceFactory
import com.pedromunhoz.domain.repository.Repository
import com.pedromunhoz.pokeapp.BuildConfig
import org.koin.dsl.module

val dataModule = module {
    single {
        PokeWebServiceFactory.makePokeWebService(
            isDebug = BuildConfig.DEBUG
        )
    }
    single {
        PokeRemoteDataSource(
            pokeApiService = get()
        ) as RemoteDataSource
    }
    single {
        PokeDataSource(context = get()) as LocalDataSource
    }
    single {
        PokeRepository(
            remoteDataSource = get(),
            localDataSource = get()
        ) as Repository
    }
}