package com.example.instagramclonev2.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclonev2.adapters.PostAdapter
import com.example.instagramclonev2.adapters.StoryAdapter
import com.example.instagramclonev2.databinding.ActivityInstagramPostBinding
import com.example.instagramclonev2.models.InstaPosts
import com.example.instagramclonev2.models.InstaStories

class InstagramPost: Fragment() {
    private lateinit var view: ActivityInstagramPostBinding
    private val InstaPosts = listOf(
        InstaPosts("Ben",  "https://upload.wikimedia.org/wikipedia/en/c/c6/The_Legend_of_Zelda_Breath_of_the_Wild.jpg","https://fastly.picsum.photos/id/60/200/200.jpg?hmac=MjMlhHlJlU_z3Z1DXohWUex2M-Gs7dtbqv4EJ4pSg3E"),
        InstaPosts("Brandon", "https://upload.wikimedia.org/wikipedia/en/0/0c/Witcher_3_cover_art.jpg", "https://fastly.picsum.photos/id/64/200/200.jpg?hmac=lJVbDn4h2axxkM72s1w8X1nQxUS3y7li49cyg0tQBZU"),
        InstaPosts("Penny", "https://upload.wikimedia.org/wikipedia/en/4/44/Red_Dead_Redemption_II.jpg","https://fastly.picsum.photos/id/15/200/200.jpg?hmac=8F3A7g2kO57xRlUcdio-9o4LDz0oEFZrYMldJkHMpVo"),
        InstaPosts("Kevin", "https://upload.wikimedia.org/wikipedia/en/8/8d/Super_Mario_Odyssey.jpg","https://fastly.picsum.photos/id/60/200/200.jpg?hmac=MjMlhHlJlU_z3Z1DXohWUex2M-Gs7dtbqv4EJ4pSg3E"),
        InstaPosts("Eain", "https://upload.wikimedia.org/wikipedia/en/a/a7/God_of_War_4_cover.jpg","https://fastly.picsum.photos/id/430/200/200.jpg?hmac=RbYQ27bVLRKt5ScfTYiQ_ePoVdo70X4eWg2KPc6JF0I"),
        InstaPosts("Hein", "https://upload.wikimedia.org/wikipedia/en/5/51/Minecraft_cover.png","https://fastly.picsum.photos/id/431/200/200.jpg?hmac=htJbypAbF5_h67SAU-qYOJLyDwNNHcHSfL67TITi2hc"),
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ActivityInstagramPostBinding.inflate(inflater, container, false)

        view.rvPostList.adapter = PostAdapter(InstaPosts)
        view.rvPostList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        view.rvPostList.isNestedScrollingEnabled = false

        return view.root
    }
}
