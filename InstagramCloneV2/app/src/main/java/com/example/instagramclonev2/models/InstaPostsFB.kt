package com.example.instagramclonev2.models

import com.google.firebase.firestore.PropertyName

data class InstaPostsFB(
    var caption: String  = "",
    @get:PropertyName("image_url") @set:PropertyName("image_url") var imageUrl: String = "",
    @get:PropertyName("created_at") @set:PropertyName("created_at") var creationTimeMs: Long = 0,
    var likes : Int = 0,
    var user: User? = null
)
