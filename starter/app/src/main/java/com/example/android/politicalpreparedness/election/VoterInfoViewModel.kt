package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import com.example.android.politicalpreparedness.util.ElectionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class VoterInfoViewModel(private val repository: ElectionRepository) : ViewModel() {


    private var viewModelJob = SupervisorJob()
    private var viewModelScope = CoroutineScope(viewModelJob+Dispatchers.Main)
    var stateData = MutableLiveData<List<State>?>()
    var intentData = MutableLiveData<String>()
    var isFollowing = MutableLiveData<Int>()
    fun getVoterInfoData(id: Int, address: String) {
        try {
            viewModelScope.launch {
                val voterInfoData = try {
                    repository.getVoterInfo(id, address).await().state
                } catch (e: Exception) {
                    emptyList<State>()
                }
                stateData.postValue(voterInfoData)
            }
        }catch (e:Exception){
            emptyList<State>()
        }

    }

    fun openBrowser(url:String){
        url?.let{
            intentData.value = url
        }
    }

    fun followElection(election: Election){
        viewModelScope.launch {
            repository.saveElection(election)
        }
    }

    fun unFollowElection(election: Election){
        viewModelScope.launch {
            repository.deleteElection(election)
        }
    }

    fun isFollowing(electionId:Int):LiveData<Int>{
        viewModelScope.launch {
            repository.isFollowing(electionId).collect {
                isFollowing.postValue(it)
            }
        }
        return isFollowing
    }
}