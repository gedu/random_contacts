package com.teddy.sweatcontacts.common.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactService {

    @GET("/api/?exc=login")
    fun getContacts(@Query("results") resultAmount: Int,
                    @Query("seed") searchSeed: String,
                    @Query("page") pageNum: Int) : Deferred<ApiResponse<ResultContactResponse>>
}