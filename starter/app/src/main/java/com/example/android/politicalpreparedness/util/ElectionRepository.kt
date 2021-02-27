package com.example.android.politicalpreparedness.util

import com.example.android.politicalpreparedness.network.CivicsApi

class ElectionRepository {
    fun getRepresentatives(address: String) = CivicsApi.retrofitService.getRepresentatives(address)

}
