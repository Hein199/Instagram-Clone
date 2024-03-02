package com.example.instagramclonev2.models

import com.google.firebase.firestore.PropertyName

data class ProfilePost(
    @get:PropertyName("profileImg") @set:PropertyName("profileImg") var profileImg: String
)
