package com.example.instagramclonev2.fragments

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagramclonev2.R
import com.example.instagramclonev2.databinding.ActivityInstagramFeedsBinding
import com.example.instagramclonev2.databinding.ActivityInstagramProfileBinding

class InstagramProfile: Fragment() {
    private lateinit var view: ActivityInstagramProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ActivityInstagramProfileBinding.inflate(inflater, container, false)
        return view.root
    }
}