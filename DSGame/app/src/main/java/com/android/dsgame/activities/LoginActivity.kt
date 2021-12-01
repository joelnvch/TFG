package com.android.dsgame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.android.dsgame.databinding.ActivityLoginBinding
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            firebaseAuth.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                .addOnSuccessListener {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this).apply {
                        setTitle("Error singing in")
                        setMessage("Incorrect username or password.")
                        setPositiveButton(
                            Html.fromHtml("<font color='#FFFFFF'>OK</font>"),
                            null
                        )
                    }.show()
                }
        }

        binding.btSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}