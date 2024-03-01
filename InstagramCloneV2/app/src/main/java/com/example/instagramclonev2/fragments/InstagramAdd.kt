package com.example.instagramclonev2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.instagramclonev2.R
import com.example.instagramclonev2.databinding.ActivityInstagramAddBinding

class InstagramAdd : Fragment() {
    private lateinit var view: ActivityInstagramAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = ActivityInstagramAddBinding.inflate(inflater, container, false)

        view.btnPost.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        view.btnStory.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        view.btnPost.setOnClickListener {
            view.btnStory.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            view.btnPost.setTextColor(ContextCompat.getColor(requireContext(), R.color.skyblue))
            childFragmentManager
                .beginTransaction()
                .replace(R.id.fcAdd, FragmentAddPost())
                .addToBackStack(FragmentAddPost::class.java.name)
                .commit()
        }

        view.btnStory.setOnClickListener {
            view.btnPost.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            view.btnStory.setTextColor(ContextCompat.getColor(requireContext(), R.color.skyblue))
            childFragmentManager
                .beginTransaction()
                .replace(R.id.fcAdd, FragmentAddStory())
                .addToBackStack(FragmentAddStory::class.java.name)
                .commit()
        }

        return view.root
    }

    private fun changeFragment(fragment: Fragment): Boolean {

        return true
    }
}