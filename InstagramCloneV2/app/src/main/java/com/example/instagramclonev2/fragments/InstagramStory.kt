package com.example.instagramclonev2.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instagramclonev2.R
import com.example.instagramclonev2.databinding.ActivityInstagramStoryBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclonev2.models.InstaStories
import com.example.instagramclonev2.adapters.StoryAdapter

class InstagramStory: Fragment() {
    private lateinit var view: ActivityInstagramStoryBinding
    private val InstaStoriesList = listOf(
        InstaStories("John", "https://upload.wikimedia.org/wikipedia/en/c/c6/The_Legend_of_Zelda_Breath_of_the_Wild.jpg"),
        InstaStories("Jonnnn", "https://upload.wikimedia.org/wikipedia/en/0/0c/Witcher_3_cover_art.jpg"),
        InstaStories("Brandon", "https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg"),
        InstaStories("Bhone", "https://upload.wikimedia.org/wikipedia/en/8/8d/Super_Mario_Odyssey.jpg"),
        InstaStories("Eain", "https://upload.wikimedia.org/wikipedia/en/a/a7/God_of_War_4_cover.jpg"),
        InstaStories("Hein", "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png"),
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ActivityInstagramStoryBinding.inflate(inflater, container, false)

        view.rvStories.adapter = StoryAdapter(InstaStoriesList)
        view.rvStories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        return view.root
    }
}