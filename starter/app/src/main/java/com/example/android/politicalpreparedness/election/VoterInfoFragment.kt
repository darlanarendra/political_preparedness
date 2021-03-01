package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.State
import com.example.android.politicalpreparedness.util.getViewModelFactory

class VoterInfoFragment : Fragment() {

    private val TAG = VoterInfoViewModel::class.java.simpleName
    lateinit var voterInfoFragmentBinding: FragmentVoterInfoBinding
    val viewModel:VoterInfoViewModel by viewModels<VoterInfoViewModel> {getViewModelFactory()}
    lateinit var election:Election
    lateinit var state: State
    var isFollowing = false

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        voterInfoFragmentBinding = FragmentVoterInfoBinding.inflate(inflater)
        voterInfoFragmentBinding.lifecycleOwner = this.viewLifecycleOwner
        election = VoterInfoFragmentArgs.fromBundle(arguments!!).election
        voterInfoFragmentBinding.viewModel = this.viewModel
        voterInfoFragmentBinding.voterInfoProgress.visibility = View.GONE
        viewModel.getVoterInfoData(election.id,election.division.state)
        viewModel.stateData.observe(viewLifecycleOwner, Observer {
            if(it?.isEmpty() == true){
                voterInfoFragmentBinding.voterInfoProgress.visibility = View.GONE
            }else{
                state = it!![0]
                voterInfoFragmentBinding.state = state
            }
        })
        viewModel.intentData.observe(viewLifecycleOwner, Observer{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it)
            startActivity(intent)
        })
        voterInfoFragmentBinding.election = election
        voterInfoFragmentBinding.button.setOnClickListener {
            if(!isFollowing){
                isFollowing = true
                voterInfoFragmentBinding.button.text = "UnFollow"
                viewModel.followElection(election)
            }else{
                voterInfoFragmentBinding.button.text = "Follow"
                isFollowing = false
                viewModel.unFollowElection(election)
            }
        }
        viewModel.isFollowing(election.id).observe(viewLifecycleOwner, Observer {
            if(it == 1){
                isFollowing = true
                voterInfoFragmentBinding.button.text = "UnFollow"
            }else{
                isFollowing = false
                voterInfoFragmentBinding.button.text = "Follow"
            }
        })
        return voterInfoFragmentBinding.root
    }



}