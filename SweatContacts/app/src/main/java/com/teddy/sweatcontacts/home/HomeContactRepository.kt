package com.teddy.sweatcontacts.home

import com.teddy.sweatcontacts.common.network.ContactRemoteSource

class HomeContactRepository(private val remoteRepository: ContactRemoteSource) {

    val listenResult get() = remoteRepository.result

    suspend fun getContacts() = remoteRepository.getContacts()
}