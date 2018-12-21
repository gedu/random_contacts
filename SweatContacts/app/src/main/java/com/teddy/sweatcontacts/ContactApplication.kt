package com.teddy.sweatcontacts

import android.app.Application
import com.teddy.sweatcontacts.common.di.mainAppModule
import com.teddy.sweatcontacts.common.di.networkModule
import org.koin.android.ext.android.startKoin

@Suppress("unused")
class ContactApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(networkModule, *mainAppModule.toTypedArray()))
    }
}