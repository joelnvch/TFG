package com.android.dsgame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.dsgame.databinding.ActivityRegisterBinding
import com.android.dsgame.managers.ConnectionManager



class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var connectionManager = ConnectionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btSignup.setOnClickListener {
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            var repeatedPassword = binding.repeatedPassword.text.toString()

            if (password != repeatedPassword) {
                AlertDialog.Builder(this).apply {
                    setTitle("Error signing up")
                    setMessage("Passwords don't match.")
                    setPositiveButton(
                        "OK",
                        null
                    )
                }.show()
                return@setOnClickListener
            }

            connectionManager.authenticator.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener{
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    AlertDialog.Builder(this).apply {
                        setTitle("Unknown error")
                        setMessage("Try again.")
                        setPositiveButton(
                            "OK",
                            null
                        )
                    }.show()
                }
        }

    }
}