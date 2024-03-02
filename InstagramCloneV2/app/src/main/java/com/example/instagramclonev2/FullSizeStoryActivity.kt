package com.example.instagramclonev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.instagramclonev2.databinding.ActivityFullSizeStoryBinding
import coil.load

class FullSizeStoryActivity : AppCompatActivity() {
    private val view: ActivityFullSizeStoryBinding by lazy { ActivityFullSizeStoryBinding.inflate(layoutInflater) }
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)

        val intent = intent
        val imageUrl = intent.getStringExtra("image_url")
        val userName = intent.getStringExtra("user_name")
        val profileImg = intent.getStringExtra("profile_img")
        Log.e("Insta story from full size", "$profileImg")
        view.ivInstaStory.load(imageUrl)
        view.tvUserName.text = userName.toString()
        view.ivInstaStory.load(profileImg)
        setupExitButton()
        startCountdownTimer()
    }

    private fun setupExitButton() {
        view.ivExit.setOnClickListener {
            cancelCountdownTimer()
            switchToMainActivity()
        }
    }
    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(5000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished / 500).toInt()
                view.progressBar.progress = progress
            }

            override fun onFinish() {
                switchToMainActivity()
            }
        }.start()
    }





    private fun cancelCountdownTimer() {
        countDownTimer?.cancel()
    }

    private fun switchToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
