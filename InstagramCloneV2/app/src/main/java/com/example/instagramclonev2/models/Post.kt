package com.example.instagramclonev2.models

data class Post(
    val caption: String,
    val image_url: String,
    val created_at: Long,
    val user: User?,
    val likes: Int
)
