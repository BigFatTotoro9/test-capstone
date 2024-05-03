package com.example.testcapstone

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testcapstone.databinding.ActivitySignInBinding
import com.example.testcapstone.utils.AuthUtil.checkCurrentUser
import com.example.testcapstone.utils.AuthUtil.showToast
import com.example.testcapstone.utils.AuthUtil.signInWithEmailAndPassword
import com.example.testcapstone.utils.AuthUtil.validateSignInForm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        auth = Firebase.auth

        val currentUser = auth.currentUser
        checkCurrentUser(this, currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            val signInFormValidation = validateSignInForm(this, email, pass)

            signInWithEmailAndPassword(email, pass) { isSuccess, exception ->
                if (isSuccess && signInFormValidation) {

                    Log.d(TAG, "loginUserWithEmail:success")
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                } else {

                    Log.e("TAG", "Error: ${exception?.message}")
                    showToast(this, exception.toString())
                }

            }
        }
    }
}