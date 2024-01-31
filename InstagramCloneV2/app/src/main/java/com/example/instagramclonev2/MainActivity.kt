package com.example.instagramclonev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.instagramclonev2.databinding.ActivityMainBinding
import com.example.instagramclonev2.fragments.InstagramAdd
import com.example.instagramclonev2.fragments.InstagramFeeds
import com.example.instagramclonev2.fragments.InstagramFeedsScroll
import com.example.instagramclonev2.fragments.InstagramProfile

class MainActivity : AppCompatActivity() {
    private val view: ActivityMainBinding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        view.bnv.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btnHome -> changeFragment(InstagramFeedsScroll())
                R.id.btnAdd-> changeFragment(InstagramAdd())
                R.id.btnProfile-> changeFragment(InstagramProfile())
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcFeeds, fragment)
            .addToBackStack(fragment::class.java.name)
            .commit()
        return true
    }
}