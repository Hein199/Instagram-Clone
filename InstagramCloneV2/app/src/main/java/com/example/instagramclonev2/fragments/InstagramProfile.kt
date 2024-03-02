package com.example.instagramclonev2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagramclonev2.adapters.PhotoAdapter
import com.example.instagramclonev2.databinding.ActivityInstagramProfileBinding
import com.example.instagramclonev2.models.ProfilePost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import coil.load

class InstagramProfile : Fragment() {
    private lateinit var viewBinding: ActivityInstagramProfileBinding
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var profileposts: MutableList<ProfilePost>
    private lateinit var auth: FirebaseAuth
    private lateinit var photoAdapter: PhotoAdapter
    private var countPost: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ActivityInstagramProfileBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileposts = mutableListOf()
        firestoreDb = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Fetch user data and posts from Firestore
        fetchUserData()

        // Set layout manager for RecyclerView
        viewBinding.rvPhoto.layoutManager = GridLayoutManager(requireContext(), 3)

        // Initialize and set adapter for RecyclerView
        photoAdapter = PhotoAdapter(profileposts)
        viewBinding.rvPhoto.adapter = photoAdapter
    }

    private fun fetchUserData() {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val docRef = firestoreDb.collection("users").document(user.uid)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val bio = document.getString("bio")
                        val displayName = document.getString("displayName")
                        val follower = document.getLong("follower")
                        val following = document.getLong("following")
                        val name = document.getString("name")
                        val profileImg = document.getString("profileImg")

                        viewBinding.bio.text = bio
                        viewBinding.displayName.text = displayName
                        viewBinding.follower.text = follower.toString() // Set follower count
                        viewBinding.following.text = following.toString() // Set following count
                        viewBinding.name.text = name


                        // Load profile image using Coil library
                        profileImg?.let {
                            viewBinding.profileImg.load(it)
                        }

                        // Fetch user's posts
                        fetchUserPosts(displayName.toString())

                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }
        }
    }

    private fun fetchUserPosts(displayName: String) {
        // Fetch user posts from Firestore
        firestoreDb.collection("posts")
            .whereEqualTo("user.displayName", displayName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                countPost = querySnapshot.size()
                Log.e("Count post", "$countPost")
                // Clear previous posts
                profileposts.clear()

                for (document in querySnapshot.documents) {
                    val profileImg = document.getString("image_url")
                    profileImg?.let {
                        profileposts.add(ProfilePost(it))
                    }
                }

                // Notify adapter that data set has changed
                photoAdapter.notifyDataSetChanged()
                viewBinding.tvPostCount.text = countPost.toString()
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", "Error getting posts: ", exception)
            }
    }
}
