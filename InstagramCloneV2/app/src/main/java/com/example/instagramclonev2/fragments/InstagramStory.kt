package com.example.instagramclonev2.fragments

import android.os.Bundle
import android.util.Log
import com.example.instagramclonev2.databinding.ActivityInstagramStoryBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclonev2.models.InstaStories
import com.example.instagramclonev2.adapters.StoryAdapter
import com.example.instagramclonev2.models.InstaPostsFB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InstagramStory: Fragment() {
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var stories: MutableList<InstaStories>
    private lateinit var currentUserStory: MutableList<InstaStories>
    private lateinit var view: ActivityInstagramStoryBinding

    private fun getCurrentEmail(): String? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.email
    }

//   private val emptyStory = MutableList(
//       InstaStories(currentUser.)
//   )
//    private val InstaStoriesList = listOf(
//        InstaStories("John", "https://upload.wikimedia.org/wikipedia/en/c/c6/The_Legend_of_Zelda_Breath_of_the_Wild.jpg"),
//        InstaStories("Jonnnn", "https://upload.wikimedia.org/wikipedia/en/0/0c/Witcher_3_cover_art.jpg"),
//        InstaStories("Brandon", "https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg"),
//        InstaStories("Bhone", "https://upload.wikimedia.org/wikipedia/en/8/8d/Super_Mario_Odyssey.jpg"),
//        InstaStories("Eain", "https://upload.wikimedia.org/wikipedia/en/a/a7/God_of_War_4_cover.jpg"),
//        InstaStories("Hein", "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png"),
//    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        stories = mutableListOf()
        currentUserStory = mutableListOf()


        firestoreDb = FirebaseFirestore.getInstance()
        val storyReference = firestoreDb.collection("stories")
            .whereNotEqualTo("user.email", getCurrentEmail())

        val currentUserStoryReference = firestoreDb.collection("stories")
            .whereEqualTo("user.email", getCurrentEmail())

        storyReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e("Instagram story", "Exception", exception)
                return@addSnapshotListener
            }
            var InstaStoriesList = snapshot.toObjects(InstaStories::class.java)
            stories.clear()
            stories.addAll(InstaStoriesList)
            view.rvStories.adapter?.notifyDataSetChanged()
        }
        currentUserStoryReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e("Instagram story from own story", "Exception", exception)
                return@addSnapshotListener
            }
            var CurrentInstaStory = snapshot.toObjects(InstaStories::class.java)
            Log.e("Instagram story from own story", "$CurrentInstaStory")
            currentUserStory.clear()
            currentUserStory.addAll(CurrentInstaStory)
            view.rvOwnStory.adapter?.notifyDataSetChanged()
        }


        view = ActivityInstagramStoryBinding.inflate(inflater, container, false)
        view.rvStories.adapter = StoryAdapter(requireContext(),stories)
        view.rvOwnStory.adapter = StoryAdapter(requireContext(),currentUserStory)
        view.rvStories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rvOwnStory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        return view.root
    }
}