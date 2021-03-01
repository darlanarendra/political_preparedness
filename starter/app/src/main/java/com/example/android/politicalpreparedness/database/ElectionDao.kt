package com.example.android.politicalpreparedness.database

import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.flow.Flow
import java.security.interfaces.ECKey


@Dao
interface ElectionDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertElection(election: Election)

    @Query("SELECT * from election_table")
    fun getAllElection() : Flow<List<Election>>

    @Query("SELECT COUNT(*) FROM election_table where id=:id")
    fun isFollowing(id:Int):Flow<Int>

    @Delete
    suspend fun deleteElection(election:Election)
}