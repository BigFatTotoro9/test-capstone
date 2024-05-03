package com.example.testcapstone

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testcapstone.databinding.ActivitySignUpBinding
import com.example.testcapstone.utils.AuthUtil.createUserWithEmailAndPassword
import com.example.testcapstone.utils.AuthUtil.showToast
import com.example.testcapstone.utils.AuthUtil.validateSignUpForm
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView.setOnClickListener {
            val signInIntent = Intent(this, SignInActivity::class.java)
            startActivity(signInIntent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            val signupFormValidation = validateSignUpForm(this, email, pass, confirmPass)

            createUserWithEmailAndPassword(email, pass) { isSuccess, exception ->
                if (isSuccess && signupFormValidation) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                } else {

                    // If sign in fails, display a message to the user.
                    Log.e("TAG", "Error: ${exception?.message}")
                    showToast(this, exception.toString())
                }
            }
        }
    }
}