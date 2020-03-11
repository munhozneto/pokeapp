package com.pedromunhoz.pokeapp.di

import com.pedromunhoz.data_remote.service.PokeWebServiceFactory
import com.pedromunhoz.pokeapp.BuildConfig
import org.koin.dsl.module

val dataModule = module {
    single {
        PokeWebServiceFactory.makePokeWebService(
            isDebug = BuildConfig.DEBUG
        )
    }
}