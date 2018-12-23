package com.teddy.sweatcontacts

import android.app.Application
import com.teddy.sweatcontacts.common.di.mainAppModule
import com.teddy.sweatcontacts.common.di.networkModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.startKoin

private const val REALM_VERSION = 1L

@Suppress("unused")
class ContactApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupRealm()

        startKoin(this, listOf(networkModule, *mainAppModule.toTypedArray()))
    }

    private fun setupRealm() {
        Realm.init(this)
        val config = with(RealmConfiguration.Builder()) {
            schemaVersion(REALM_VERSION)
            deleteRealmIfMigrationNeeded()
            build()
        }
        Realm.setDefaultConfiguration(config)
    }
}