package com.example.instagramclonev2.fragments

import android.app.Activity.RESULT_OK
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
import com.example.instagramclonev2.databinding.FragmentAddStoryBinding
import com.example.instagramclonev2.models.Story
import com.example.instagramclonev2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

private const val TAG = "FragmentAddStory"
class FragmentAddStory : Fragment() {
    private lateinit var view: FragmentAddStoryBinding
    private var signedInUser: User? = null
    private lateinit var firestoreDb: FirebaseFirestore
    private lateinit var storageReference: StorageReference

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try{
            view.ivStoryPreview.setImageURI(galleryUri)
            view.ivStoryPreview.tag = galleryUri
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageBitmap: Bitmap? = result.data?.extras?.get("data") as Bitmap?
            if (imageBitmap != null) {
                // Convert the Bitmap to a Uri
                val imageUri = getImageUri(requireContext(), imageBitmap)
                try {
                    view.ivStoryPreview.setImageURI(imageUri)
                    view.ivStoryPreview.tag = imageUri
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
        view = FragmentAddStoryBinding.inflate(inflater, container, false)

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

        view.ibCamera1.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }

        view.ibGallery1.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        view.btnUploadStory.setOnClickListener {
            handleUploadButtonClick()
        }

        return view.root
    }

    private fun getImageUri(context: Context, image: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, image, "Title", null)
        return Uri.parse(path)
    }

    private fun handleUploadButtonClick() {
        val imageUri = view.ivStoryPreview.tag as Uri?

        if (imageUri == null) {
            Toast.makeText(requireContext(), "No photo selected", Toast.LENGTH_SHORT).show()
            return
        }
        if (signedInUser == null) {
            Toast.makeText(requireContext(), "No signed in user", Toast.LENGTH_SHORT).show()
            return
        }

        view.btnUploadStory.isEnabled = false
        val photoReference = storageReference.child("images/${System.currentTimeMillis()}-photo.jpg")
        photoReference.putFile(imageUri)
            .continueWithTask {photoUploadTask ->
                Log.i(TAG, "upload bytes: ${photoUploadTask.result?.bytesTransferred}")
                photoReference.downloadUrl
            }
            .continueWithTask {downloadUrlTask ->
                val story = Story(
                    downloadUrlTask.result.toString(),
                    signedInUser,
                )
                firestoreDb.collection("stories").add(story)
            }
            .addOnCompleteListener { postCreationTask ->
                view.btnUploadStory.isEnabled = true
                if (!postCreationTask.isSuccessful) {
                    Log.e(TAG, "Exception during Firebase operations", postCreationTask.exception)
                    Toast.makeText(requireContext(), "Failed to save post", Toast.LENGTH_SHORT).show()
                }
                view.ivStoryPreview.setImageResource(0)
                Toast.makeText(requireContext(), "Upload Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity:: class.java)
                startActivity(intent)
            }
    }
}