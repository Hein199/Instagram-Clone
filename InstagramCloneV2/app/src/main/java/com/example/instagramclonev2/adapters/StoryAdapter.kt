package com.example.instagramclonev2.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonev2.databinding.StoryItemBinding
import com.example.instagramclonev2.models.InstaStories
import coil.load

class StoryAdapter(private val instaStories: List<InstaStories>)
    : RecyclerView.Adapter<StoryAdapter.RecyclerViewHolder>() {
    class RecyclerViewHolder ( val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = instaStories.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val story = instaStories[position]
        holder.binding.tvStoryUserName.text = story.userName.toString()
        holder.binding.ivStoryImg.load(story.storyImg)

    }
}