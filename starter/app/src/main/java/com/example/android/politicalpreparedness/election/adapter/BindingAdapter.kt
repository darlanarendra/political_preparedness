package com.example.android.politicalpreparedness.election.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.example.android.politicalpreparedness.representative.model.Representative

@BindingAdapter("setRepresentativeListAdapter")
fun setRepresentativeAdapter(recyclerView: RecyclerView, items:List<Representative>?) {
    items?.let{
        (recyclerView.adapter as RepresentativeListAdapter).submitList(it)
    }
    recyclerView?.apply {
        layoutManager = LinearLayoutManager(recyclerView.context)
    }
}


@BindingAdapter("setAdapter")
fun selElectionAdapter(recyclerView:RecyclerView, items:List<Election>){
    items?.let{
        (recyclerView.adapter as ElectionListAdapter).submitList(it)
    }
}