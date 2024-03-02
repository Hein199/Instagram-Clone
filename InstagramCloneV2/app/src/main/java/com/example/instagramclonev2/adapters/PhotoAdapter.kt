package com.example.instagramclonev2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonev2.databinding.ItemPhotoBinding
import com.example.instagramclonev2.models.ProfilePost
import coil.load
import com.example.instagramclonev2.models.InstaPostsFB

class PhotoAdapter(private val profilePosts: MutableList<InstaPostsFB>)
    : RecyclerView.Adapter<PhotoAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = profilePosts.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val post = profilePosts[position]
        holder.binding.ivPostPhoto.load(post.imageUrl)
    }
}