package com.example.android.politicalpreparedness.util

import com.example.android.politicalpreparedness.ElectionApplication
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter

class ElectionRepository {
    private val databaseInstance = ElectionDatabase.getInstance(ElectionApplication.instance)
    private val electionDao:ElectionDao = databaseInstance.electionDao
    fun getRepresentatives(address: String) = CivicsApi.retrofitService.getRepresentatives(address)
    fun getElections() = CivicsApi.retrofitService.getElections()
    fun getAllElection() = electionDao.getAllElection()

}
