package com.example.instagramclonev2.models

data class User (
    var bio: String = "",
    var displayName: String = "",
    var follower: Int = 0,
    var following: Int = 0,
    var name: String = "",
    var profileImg: String = ""
)