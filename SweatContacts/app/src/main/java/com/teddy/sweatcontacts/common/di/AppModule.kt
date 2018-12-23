package com.teddy.sweatcontacts.common.di

import com.teddy.sweatcontacts.common.network.ContactRemoteSource
import com.teddy.sweatcontacts.common.network.PaginationManager
import com.teddy.sweatcontacts.common.storage.LocalStorageSource
import com.teddy.sweatcontacts.detail.DetailContactViewModel
import com.teddy.sweatcontacts.detail.DetailContractRepository
import com.teddy.sweatcontacts.home.HomeContactRepository
import com.teddy.sweatcontacts.home.HomeContactViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

private val appModule = module {

    factory { ContactRemoteSource(get()) }

    factory { LocalStorageSource() }

    single { PaginationManager() }
}

private val homeModule = module {

    viewModel { HomeContactViewModel(get(), Dispatchers.Main) }

    factory { HomeContactRepository(get(), get(), get()) }

}

private val detailModule = module {

    viewModel { DetailContactViewModel(get(), Dispatchers.Main) }

    factory { DetailContractRepository(get()) }
}

val mainAppModule = listOf(appModule, homeModule, detailModule)