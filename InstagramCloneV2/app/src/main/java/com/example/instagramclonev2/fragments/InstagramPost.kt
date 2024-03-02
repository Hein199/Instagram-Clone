package com.example.instagramclonev2.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclonev2.adapters.PostAdapter
import com.example.instagramclonev2.databinding.ActivityInstagramPostBinding
import com.example.instagramclonev2.models.InstaPosts
import com.example.instagramclonev2.models.InstaPostsFB
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class InstagramPost: Fragment() {
    private lateinit var view: ActivityInstagramPostBinding
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var posts: MutableList<InstaPostsFB>

    private fun getCurrentEmail(): String? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser?.email
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        posts = mutableListOf()
        firestoreDb = FirebaseFirestore.getInstance()
        val currentUserEmail = getCurrentEmail()
        Log.i("current user id ", "${getCurrentEmail()}")
        val postReference = firestoreDb.collection("posts")
            .whereNotEqualTo("user.email", currentUserEmail)
            //.orderBy("created_at", Query.Direction.DESCENDING)
        Log.i("post referenece from instagram post", "${postReference}" )
        postReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e("Instagram Post", "Exception", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(InstaPostsFB::class.java)
            postList.sortByDescending { it.creationTimeMs }
            posts.clear()
            posts.addAll(postList)
            view.rvPostList.adapter?.notifyDataSetChanged()
        }

        view = ActivityInstagramPostBinding.inflate(inflater, container, false)

        view.rvPostList.adapter = PostAdapter(posts)
        view.rvPostList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return view.root
    }
}
