package com.example.android.politicalpreparedness.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
import com.example.android.politicalpreparedness.representative.RepresentativeViewModel
import java.lang.IllegalArgumentException

/**
 * Factory for all View Models
 */
class ViewModelFactory(private val electionRepository:ElectionRepository,
                       owner: SavedStateRegistryOwner,
                       defaultArgs: Bundle? = null) :AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle)= with(modelClass) {
        when{
            isAssignableFrom(RepresentativeViewModel::class.java)->
                RepresentativeViewModel(electionRepository)
            isAssignableFrom(ElectionsViewModel::class.java)->
                ElectionsViewModel(electionRepository)
            isAssignableFrom(VoterInfoViewModel::class.java)->
                VoterInfoViewModel(electionRepository)
            else->
                throw IllegalArgumentException("Uknown Error")

        }as T
    }

}
