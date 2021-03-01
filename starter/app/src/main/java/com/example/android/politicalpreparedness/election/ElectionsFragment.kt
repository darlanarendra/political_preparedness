package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.util.getViewModelFactory

class ElectionsFragment: Fragment() {

    private lateinit var electionListAdapter: ElectionListAdapter
    private lateinit var followingListAdapter:ElectionListAdapter
    val viewModel by viewModels<ElectionsViewModel> { getViewModelFactory() }
    private lateinit var fragmentElectionBinding:FragmentElectionBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentElectionBinding = FragmentElectionBinding.inflate(inflater, container,false)?.apply {
            electionViewModel = viewModel
        }
        return fragmentElectionBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragmentElectionBinding.lifecycleOwner = this.viewLifecycleOwner
        setupAdapters()
    }

    private fun setupAdapters() {
        val vm = fragmentElectionBinding.electionViewModel
        if(vm!=null) {
            electionListAdapter = ElectionListAdapter {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
            }
            fragmentElectionBinding.upcomingElectionRecyclerView.adapter = electionListAdapter
            viewModel.electionListItems.observe(viewLifecycleOwner, Observer {
               if(it.isEmpty()){
                   fragmentElectionBinding.progressBar.visibility = View.VISIBLE
               }else{
                   fragmentElectionBinding.progressBar.visibility = View.GONE
               }
            })

            followingListAdapter = ElectionListAdapter {
                findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(it))
            }
            fragmentElectionBinding.savedElectionRecyclerView.adapter = followingListAdapter
        }else{
            println("View Model is  not initialized")
        }
    }


}