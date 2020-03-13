package com.pedromunhoz.pokeapp.di

import com.pedromunhoz.domain.executor.PostExecutionThread
import com.pedromunhoz.pokeapp.ui.executor.UiThread
import org.koin.dsl.module

val androidModule = module {
    single { this }
    single { UiThread() as PostExecutionThread }
}