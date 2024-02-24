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
import com.google.firebase.firestore.FirebaseFirestore

class InstagramPost: Fragment() {
    private lateinit var view: ActivityInstagramPostBinding
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var posts: MutableList<InstaPostsFB>

    private val InstaPosts = listOf(
        InstaPosts("Ben",  "https://upload.wikimedia.org/wikipedia/en/c/c6/The_Legend_of_Zelda_Breath_of_the_Wild.jpg","https://fastly.picsum.photos/id/60/200/200.jpg?hmac=MjMlhHlJlU_z3Z1DXohWUex2M-Gs7dtbqv4EJ4pSg3E", 35, "Life is not lifing"),
        InstaPosts("Brandon", "https://upload.wikimedia.org/wikipedia/en/0/0c/Witcher_3_cover_art.jpg", "https://fastly.picsum.photos/id/64/200/200.jpg?hmac=lJVbDn4h2axxkM72s1w8X1nQxUS3y7li49cyg0tQBZU",35, "Woosh! Nice"),
        InstaPosts("Penny", "https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg","https://fastly.picsum.photos/id/15/200/200.jpg?hmac=8F3A7g2kO57xRlUcdio-9o4LDz0oEFZrYMldJkHMpVo",90, "Way to go Champ!"),
        InstaPosts("Kevin", "https://upload.wikimedia.org/wikipedia/en/8/8d/Super_Mario_Odyssey.jpg","https://fastly.picsum.photos/id/60/200/200.jpg?hmac=MjMlhHlJlU_z3Z1DXohWUex2M-Gs7dtbqv4EJ4pSg3E",100, "I love you rose nim"),
        InstaPosts("Eain", "https://upload.wikimedia.org/wikipedia/en/a/a7/God_of_War_4_cover.jpg","https://fastly.picsum.photos/id/430/200/200.jpg?hmac=RbYQ27bVLRKt5ScfTYiQ_ePoVdo70X4eWg2KPc6JF0I",123, "To infinity and beyond"),
        InstaPosts("Hein", "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png","https://fastly.picsum.photos/id/431/200/200.jpg?hmac=htJbypAbF5_h67SAU-qYOJLyDwNNHcHSfL67TITi2hc",12345,  "What if what if we run away!"),
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        Log.i("instaposts array list", "${InstaPosts}")
        posts = mutableListOf()
        firestoreDb = FirebaseFirestore.getInstance()
        val postReference = firestoreDb.collection("posts").limit(20)
        postReference.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e("Instagram Post", "Exception", exception)
                return@addSnapshotListener
            }
            val postList = snapshot.toObjects(InstaPostsFB::class.java)
            posts.clear()
            posts.addAll(postList)
            view.rvPostList.adapter?.notifyDataSetChanged()

            Log.i("firebase arrayList", "$postList")
            for (post in postList) {
                Log.i("Instagram Post: ", "Post:  $post")
                //Toast.makeText(this,"Documents ${doc.id}: ${doc.data}", Toast.LENGTH_SHORT  ).show()
            }
        }

        view = ActivityInstagramPostBinding.inflate(inflater, container, false)

        view.rvPostList.adapter = PostAdapter(posts)
        view.rvPostList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


//        view.rvPostList.adapter = PostAdapter(InstaPosts)
//        view.rvPostList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        return view.root
    }

//    private fun getPosts() {
//        dbref = FirebaseDatabase.getInstance().getReference("posts")
//        dbref.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()){
//                    for (userSnapshot in snapshot.children){
//                        val posts = userSnapshot.getValue(InstaPostsFB::class.java)
//                        postArrayList.add(posts!!)
//                    }
//                    //userRecyclerview.adapter = MyAdapter(userArrayList)
//                    Log.i("Instagram firebase", "$postArrayList")
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }




}
