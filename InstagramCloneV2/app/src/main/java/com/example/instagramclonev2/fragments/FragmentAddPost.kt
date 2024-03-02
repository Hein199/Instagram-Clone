package com.example.instagramclonev2.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.instagramclonev2.MainActivity
import com.example.instagramclonev2.databinding.FragmentAddPostBinding
import com.example.instagramclonev2.models.Post
import com.example.instagramclonev2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

private const val TAG = "FragmentAddPost"
class FragmentAddPost: Fragment() {
    private lateinit var view: FragmentAddPostBinding
    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try{
            view.ivPreview.setImageURI(galleryUri)
            view.ivPreview.tag = galleryUri
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageBitmap: Bitmap? = result.data?.extras?.get("data") as Bitmap?
            if (imageBitmap != null) {
                // Convert the Bitmap to a Uri
                val imageUri = getImageUri(requireContext(), imageBitmap)
                try {
                    view.ivPreview.setImageURI(imageUri)
                    view.ivPreview.tag = imageUri
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentAddPostBinding.inflate(inflater, container, false)

        storageReference = FirebaseStorage.getInstance().reference
        firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener {exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }

        view.ibGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        view.ibCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }

        view.btnUpload.setOnClickListener {
            handelUploadButtonClick()
        }

        return view.root
    }

    private fun getImageUri(context: Context, image: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, image, "Title", null)
        return Uri.parse(path)
    }

    private fun handelUploadButtonClick() {
        val imageUri = view.ivPreview.tag as Uri?

        if (imageUri == null) {
            Toast.makeText(requireContext(), "No photo selected", Toast.LENGTH_SHORT).show()
            return
        }
        if (view.etCaption.text.isBlank()) {
            Toast.makeText(requireContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (signedInUser == null) {
            Toast.makeText(requireContext(), "No signed in user", Toast.LENGTH_SHORT).show()
            return
        }

        view.btnUpload.isEnabled = false
        val photoReference = storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")
        photoReference.putFile(imageUri)
            .continueWithTask {photoUploadTask ->
                Log.i(TAG, "upload bytes: ${photoUploadTask.result?.bytesTransferred}")
                photoReference.downloadUrl
            }
            .continueWithTask {downloadUrlTask ->
                val post = Post(
                    view.etCaption.text.toString(),
                    downloadUrlTask.result.toString(),
                    System.currentTimeMillis(),
                    signedInUser,
                    0
                )
                firestoreDb.collection("posts").add(post)
            }
            .addOnCompleteListener { postCreationTask ->
                view.btnUpload.isEnabled = true
                if (!postCreationTask.isSuccessful) {
                    Log.e(TAG, "Exception during Firebase operations", postCreationTask.exception)
                    Toast.makeText(requireContext(), "Failed to save post", Toast.LENGTH_SHORT).show()
                }
                view.etCaption.text.clear()
                view.ivPreview.setImageResource(0)
                Toast.makeText(requireContext(), "Upload Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity:: class.java)
                startActivity(intent)
            }
    }
}