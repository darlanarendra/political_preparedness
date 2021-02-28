package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.util.getViewModelFactory

class ElectionsFragment: Fragment() {

    val viewModel by viewModels<ElectionsViewModel> { getViewModelFactory() }
    private lateinit var fragmentElectionBinding:FragmentElectionBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentElectionBinding = FragmentElectionBinding.inflate(inflater)?.apply {
            electionViewModel = viewModel
        }
        fragmentElectionBinding.lifecycleOwner = this.viewLifecycleOwner
        setupAdapters()
        return fragmentElectionBinding.root
    }

    private fun setupAdapters() {
        viewModel.electionListItems.observe(viewLifecycleOwner, Observer { 
            
        })
    }


}