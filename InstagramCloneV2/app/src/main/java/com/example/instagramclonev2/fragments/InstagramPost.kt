package com.example.instagramclonev2.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagramclonev2.R
import com.example.instagramclonev2.databinding.ActivityInstagramPostBinding
import com.example.instagramclonev2.databinding.ActivityInstagramProfileBinding

class InstagramPost: Fragment() {
    private lateinit var view: ActivityInstagramPostBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ActivityInstagramPostBinding.inflate(inflater, container, false)
        return view.root
    }
}