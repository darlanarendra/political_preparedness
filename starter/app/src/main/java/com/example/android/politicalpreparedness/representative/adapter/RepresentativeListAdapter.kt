package com.example.android.politicalpreparedness.representative.adapter

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.RepresentativeItemBinding
import com.example.android.politicalpreparedness.network.models.Channel
import com.example.android.politicalpreparedness.representative.model.Representative

class RepresentativeListAdapter : ListAdapter<Representative,RepresentativeViewHolder>(RepresentativeCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepresentativeViewHolder {
        return RepresentativeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepresentativeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}

class RepresentativeCallback : DiffUtil.ItemCallback<Representative>(){
    override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem.office.division.id == newItem.office.division.id
    }

    override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem == newItem
    }

}

class RepresentativeViewHolder(val binding: RepresentativeItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item:Representative) = with(binding){
        binding.representative = item
        binding.representativePhoto.setImageResource(R.drawable.ic_profile)
        item.official.channels?.let {
            showSocialLinks(it)
        }
        item.official.urls?.let{
            showWWWLinks(it)
        }
        binding.executePendingBindings()
    }

    private fun showWWWLinks(urls: List<String>): Unit {
        enableLink(binding.wwwIcon, urls.first() )
    }

    private fun enableLink(wwwIcon: ImageView, url: String) {
        wwwIcon.visibility = View.VISIBLE
        wwwIcon.setOnClickListener {
            openURL(url)
        }
    }

    private fun openURL(url: String) {
        val intent = Intent(ACTION_VIEW,Uri.parse(url))
        itemView.context.startActivity(intent)
    }

    private fun showSocialLinks(channels: List<Channel>): Unit {
        val facebookUrl = getFacebookUrl(channels)
        if(!facebookUrl.isNullOrBlank()){
            enableLink(binding.facebookIcon,facebookUrl)
        }
        val twitterUrl = getTwitterUrl(channels)
        if(!twitterUrl.isNullOrBlank()){
            enableLink(binding.twitterIcon, twitterUrl)
        }
    }

    private fun getTwitterUrl(channels: List<Channel>): String? {
       return channels?.filter {
           it.type == "Twitter"
       }.map {
           "https://twitter.com/${it.id}"
       }.firstOrNull()
    }

    private fun getFacebookUrl(channels: List<Channel>): String? {
        return channels.filter {
            it.type == "Facebook"
        }.map {
            "https://facebook.com/${it.id}"
        }.firstOrNull()
    }

    companion object{
        fun from(parent:ViewGroup):RepresentativeViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RepresentativeItemBinding.inflate(layoutInflater)
            return RepresentativeViewHolder(binding)
        }
    }
}
