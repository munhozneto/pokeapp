package com.pedromunhoz.pokeapp

import android.app.Application
import com.pedromunhoz.pokeapp.di.androidModule
import com.pedromunhoz.pokeapp.di.dataModule
import com.pedromunhoz.pokeapp.di.domainModule
import com.pedromunhoz.pokeapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CustomApplication)
            modules(
                listOf(
                    androidModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}