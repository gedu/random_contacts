package com.teddy.sweatcontacts.common.di

import com.teddy.sweatcontacts.common.network.ContactRemoteSource
import com.teddy.sweatcontacts.home.HomeContactRepository
import com.teddy.sweatcontacts.home.HomeContactViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val appModule = module {

    factory { ContactRemoteSource(get()) }

}

private val homeModule = module {

    viewModel { HomeContactViewModel(get(), Dispatchers.Main) }

    factory { HomeContactRepository(get()) }

}

val mainAppModule = listOf(appModule, homeModule)