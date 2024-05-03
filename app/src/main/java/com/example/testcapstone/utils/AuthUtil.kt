package com.example.testcapstone.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.testcapstone.MainActivity
import com.example.testcapstone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthUtil {

    private val auth = FirebaseAuth.getInstance()


    fun validateSignUpForm(
        context: Context,
        email: String,
        pass: String,
        confirmPass: String
    ): Boolean {
        if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            showToast(context, context.getString(R.string.error_empty_fields))
            return false
        }

        if (pass != confirmPass) {
            showToast(context, context.getString(R.string.error_password_mismatch))
            return false
        }
        return true
    }


    fun validateSignInForm(context: Context, email: String, pass: String): Boolean {
        return validateSignUpForm(context, email, pass, pass)
    }

    fun createUserWithEmailAndPassword(
        email: String,
        pass: String,
        onComplete: (Boolean, Exception?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception)
                }
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        pass: String,
        onComplete: (Boolean, Exception?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(true, task.exception)
                }
            }
    }


    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun checkCurrentUser(context: Context, currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val mainIntent = Intent(context, MainActivity::class.java)
            startActivity(context, mainIntent, null)
        }
    }
}