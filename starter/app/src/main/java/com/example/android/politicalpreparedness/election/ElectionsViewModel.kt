package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.util.ElectionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class ElectionsViewModel(private val electionRepository: ElectionRepository): ViewModel() {

    private val _electionsData = MutableLiveData<List<Election>>()
    val electionListItems :LiveData<List<Election>> = _electionsData
    private val _favouriteElections = MutableLiveData<List<Election>>()
    val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob+ Dispatchers.Main)
    init {
        getElectionData()
        //getFavouriteElections()
    }

    private fun getElectionData(){
            try {
                viewModelScope.launch {
                    val electionData = try {
                        electionRepository.getElections().await().elections
                    } catch (e: Exception) {
                        emptyList<Election>()
                    }
                    _electionsData.postValue(electionData)
                }
            }catch(e:Exception){
                e.printStackTrace()
            }
    }

//    private fun getFavouriteElections():LiveData<List<Election>>{
//            viewModelScope.launch {
//                electionRepository.getAllElection().collect{
//                    _favouriteElections.postValue(it)
//                }
//            }
//        return _favouriteElections
//    }

}