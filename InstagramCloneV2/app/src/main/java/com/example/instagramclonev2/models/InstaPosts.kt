
package com.example.instagramclonev2.models

import com.google.firebase.database.PropertyName

data class InstaPosts(
    val userName: String,
    val postImg: String,
    val postUserImg: String,
    val likeCount: Int,
    val postCaption: String

)
//image_url="https://firebasestorage.googleapis.com/v0/b/instagramclonev2-b150c.appspot.com/o/images%2F57B4EB10-EF4E-49FF-8C51-97507D3C36DC-6398-0000048BA21488B0.jpeg?alt=media&token=d4ecf9ab-b003-42d4-907d-b3f2d4d4b7ab",
//caption="Having a great time in Sapa",
//created_at=1708772310697,
//user={displayName=Bhone, name=bhonepyaekyaw_},
//likes=10
//data class InstaPosts(
//    @get:PropertyName("image_url") @set:PropertyName("image_url") var imageUrl: String,
//    var caption: String,
//    @get:PropertyName("created_at") @set:PropertyName("create_at") var createdAt: Long,
//    var user: User? = null,
//    var likes: Int
//)

