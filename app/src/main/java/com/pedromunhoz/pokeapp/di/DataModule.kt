package com.pedromunhoz.pokeapp.di

import com.pedromunhoz.data.PokeRepository
import com.pedromunhoz.data.RemoteDataSource
import com.pedromunhoz.data_local.PokeDataSource
import com.pedromunhoz.data_remote.service.PokeWebServiceFactory
import com.pedromunhoz.pokeapp.BuildConfig
import org.koin.dsl.module

val dataModule = module {
    single {
        PokeWebServiceFactory.makePokeWebService(
            isDebug = BuildConfig.DEBUG
        )
    }
    single {
        PokeDataSource(context = get())
    }
    single {
        PokeRepository(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}