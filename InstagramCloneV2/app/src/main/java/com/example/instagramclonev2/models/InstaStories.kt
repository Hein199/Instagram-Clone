package com.example.instagramclonev2.models

import android.media.Image
import com.google.firebase.firestore.PropertyName

data class InstaStories(
    var storyImageUrl: String = "",
    var user: User? = null

)
