package com.example.instagramclonev2.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagramclonev2.databinding.ActivityInstagramFeedsBinding

class InstagramFeeds: Fragment() {
    private lateinit var view: ActivityInstagramFeedsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ActivityInstagramFeedsBinding.inflate(inflater, container, false)
        return view.root
    }
}