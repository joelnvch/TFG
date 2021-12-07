package com.android.dsgame.activities

import android.R.attr.name
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.databinding.ActivityLoginBinding
import com.android.dsgame.managers.ConnectionManager


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var connectionManager = ConnectionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            if (binding.etEmail.text.toString() == "" || binding.etPassword.text.toString() == "") {
                AlertDialog.Builder(this).apply {
                    setTitle("Error singing in")
                    setMessage("Incorrect username or password.")
                    setPositiveButton(
                        "OK",
                        null
                    )
                }.show()
                return@setOnClickListener
            }

            connectionManager.authenticator.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                .addOnSuccessListener {
                    board.userId = connectionManager.authenticator.currentUser!!.uid
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this).apply {
                        setTitle("Error singing in")
                        setMessage("Incorrect username or password.")
                        setPositiveButton(
                            "OK",
                            null
                        )
                    }.show()
                }
        }

        binding.btSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}