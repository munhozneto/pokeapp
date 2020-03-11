package com.pedromunhoz.data_remote.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PokeWebServiceFactory {
    fun makePokeWebService(isDebug: Boolean): PokeApiService {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor(isDebug))
        return makePokeWebService(okHttpClient)
    }

    private fun makePokeWebService(okHttpClient: OkHttpClient): PokeApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(PokeApiSettings.API_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PokeApiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                networkInterceptors().add(Interceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()
                    chain.proceed(request)
                })
            }
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }
}