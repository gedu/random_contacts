package com.teddy.sweatcontacts.common.di

import com.teddy.sweatcontacts.BuildConfig
import com.teddy.sweatcontacts.common.network.ApiResponseCallAdapterFactory
import com.teddy.sweatcontacts.common.network.ContactService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single { getContactService(get()) }

    single { getRetrofit(get()) }

    single { getHttpClient() }
}


private fun getContactService(retrofit: Retrofit): ContactService {
    return retrofit.create(ContactService::class.java)
}

private fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.HOST)
        .addCallAdapterFactory(ApiResponseCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun getHttpClient(): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder ()

    clientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(httpLogLevel))

    return clientBuilder.build()
}

private val httpLogLevel by lazy {
    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
}