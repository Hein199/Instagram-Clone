package com.example.instagramclonev2.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonev2.databinding.StoryItemBinding
import com.example.instagramclonev2.models.InstaStories
import coil.load
import com.example.instagramclonev2.databinding.InstagramPostItemBinding
import com.example.instagramclonev2.models.InstaPosts

class PostAdapter(private val instaPosts: List<InstaPosts>)
    : RecyclerView.Adapter<PostAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder ( val binding: InstagramPostItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = InstagramPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = instaPosts.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val post = instaPosts[position]
        holder.binding.tvUserPostName.text = post.userName.toString()
        holder.binding.ivUserPostImg.load(post.postUserImg)
        holder.binding.ivPostImg.load(post.postImg)

    }
}