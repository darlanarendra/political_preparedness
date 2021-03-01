package com.example.android.politicalpreparedness.util

import com.example.android.politicalpreparedness.ElectionApplication
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election

class ElectionRepository {
    private val databaseInstance = ElectionDatabase.getInstance(ElectionApplication.instance)
    private val electionDao:ElectionDao = databaseInstance.electionDao
    fun getRepresentatives(address: String) = CivicsApi.retrofitService.getRepresentatives(address)
    fun getElections() = CivicsApi.retrofitService.getElections()
    fun getAllElection() = electionDao.getAllElection()
    fun getVoterInfo(id:Int, address:String) = CivicsApi.retrofitService.getVoterInfo(id, address)
    suspend fun saveElection(election: Election) {
        electionDao.insertElection(election)
    }

    suspend fun deleteElection(election: Election) {
        electionDao.deleteElection(election)
    }

    fun isFollowing(electionId: Int) = electionDao.isFollowing(electionId)
}
