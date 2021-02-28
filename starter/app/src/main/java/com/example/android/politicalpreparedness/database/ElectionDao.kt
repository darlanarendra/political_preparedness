package com.example.android.politicalpreparedness.database

import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.flow.Flow



@Dao
interface ElectionDao {

    //TODO: Add insert query

    //TODO: Add select all election query

    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query

    @Query("SELECT * from election_table")
    fun getAllElection() : Flow<List<Election>>
}