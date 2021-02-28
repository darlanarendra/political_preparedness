package com.example.android.politicalpreparedness.election.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android.politicalpreparedness.network.models.Election


class ElectionListAdapter(val onItemClicked:(Election)->Unit): ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback()) {

    class ElectionViewHolder {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder{

    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {

    }

}

class ElectionDiffCallback :DiffUtil.ItemCallback<Election>(){
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        TODO("Not yet implemented")
    }

}
