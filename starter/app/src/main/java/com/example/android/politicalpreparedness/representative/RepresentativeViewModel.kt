package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import com.example.android.politicalpreparedness.util.ElectionRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class RepresentativeViewModel(val repository:ElectionRepository): ViewModel() {

    val _representatives = MutableLiveData<List<Representative>>()
    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    fun getRepresentatives(address: String):LiveData<List<Representative>>{
        viewModelScope.launch {
            try {
                val (offices, officials) = repository.getRepresentatives(address).await()
                _representatives.value = offices.flatMap { office-> office.getRepresentatives(officials)}
            }catch(e:Exception){
                println("getRepresentatives"+e.message)
                e.printStackTrace()
            }

        }
        return _representatives
    }
}
