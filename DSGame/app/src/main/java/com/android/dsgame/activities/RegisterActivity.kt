package com.android.dsgame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AlertDialog
import com.android.dsgame.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth



class RegisterActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val binding = ActivityRegisterBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btSignup.setOnClickListener {
            if (binding.password.toString() != binding.repeatedPassword.toString())
                AlertDialog.Builder(this).apply {
                    setTitle("Error signing up")
                    setMessage("Passwords don't match.")
                    setPositiveButton(
                        Html.fromHtml("<font color='#FFFFFF'>OK</font>"),
                        null
                    )
                }.show()

            auth.createUserWithEmailAndPassword(binding.email.toString(), binding.password.toString())
                .addOnSuccessListener{
                    val cu = auth.currentUser
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    AlertDialog.Builder(this).apply {
                        setTitle("Unknown error")
                        setMessage("Try again.")
                        setPositiveButton(
                            Html.fromHtml("<font color='#FFFFFF'>OK</font>"),
                            null
                        )
                    }.show()
                }
        }

    }
}