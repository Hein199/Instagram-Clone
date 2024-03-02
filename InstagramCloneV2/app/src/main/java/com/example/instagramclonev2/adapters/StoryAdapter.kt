package com.example.instagramclonev2.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclonev2.databinding.StoryItemBinding
import com.example.instagramclonev2.models.InstaStories
import coil.load
import com.example.instagramclonev2.FullSizeStoryActivity

class StoryAdapter(
    private val context: Context,
    private val instaStories: List<InstaStories>
) : RecyclerView.Adapter<StoryAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = instaStories.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val story = instaStories[position]
        holder.binding.tvStoryUserName.text = story.user?.displayName.toString()
        holder.binding.ivStoryImg.load(story.user?.profileImg)
        holder.binding.ivStoryImg.setOnClickListener {
            val intent = Intent(context, FullSizeStoryActivity::class.java)
            intent.putExtra("image_url", story.storyImageUrl)
            intent.putExtra("user_name", story.user?.displayName)
            intent.putExtra("profile_img", story.user?.profileImg)
            context.startActivity(intent)
        }
    }
}
