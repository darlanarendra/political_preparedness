package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionGlobalRecyclerviewItemBinding
import com.example.android.politicalpreparedness.network.models.Election


class ElectionListAdapter(val onItemClicked:(Election)->Unit): ListAdapter<Election, ElectionListAdapter.ElectionViewHolder>(ElectionDiffCallback()) {

    class ElectionViewHolder(private val binding:ElectionGlobalRecyclerviewItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(election:Election, onItemClicked:(Election)->Unit){
            binding.election = election
            binding.root.setOnClickListener{
                onItemClicked(election)
            }
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ElectionViewHolder{
                val layoutInflator = LayoutInflater.from(parent.context)
                val binding = ElectionGlobalRecyclerviewItemBinding.inflate(layoutInflator)
                return ElectionViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder{
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        holder.bind(getItem(position),onItemClicked)
    }

}

class ElectionDiffCallback :DiffUtil.ItemCallback<Election>(){
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }

}
