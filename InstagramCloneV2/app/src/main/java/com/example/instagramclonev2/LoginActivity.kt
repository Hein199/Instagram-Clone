package com.example.instagramclonev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.instagramclonev2.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Tag

class LoginActivity : AppCompatActivity() {

    private val view: ActivityLoginBinding by lazy{ActivityLoginBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)



        view.btnLogin.setOnClickListener{
            view.btnLogin.isEnabled = false
            val email = view.tvEmail.text.toString()
            val password = view.tvPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this,"Email or Password is empty.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val auth = FirebaseAuth.getInstance()

//            if (auth.currentUser != null) {
//                goMain()
//            }
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{task ->
                view.btnLogin.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(this,"Login Success", Toast.LENGTH_SHORT).show()
                    goMain()
                }else{
                    Toast.makeText(this,"Fail", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun goMain() {
        val intent = Intent(this, MainActivity:: class.java)
        startActivity(intent)
    }


}