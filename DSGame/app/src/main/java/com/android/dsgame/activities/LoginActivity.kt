package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.databinding.ActivityLoginBinding
import com.android.dsgame.managers.DatabaseManager
import com.android.dsgame.model.Board
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authenticator = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            val dbm = DatabaseManager(this)
            val dialog = dbm.createLoadingDialog()

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

            dialog.show()
            authenticator.signInWithEmailAndPassword(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                .addOnSuccessListener {
                    MyApplication.board.userId = authenticator.currentUser!!.uid
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
                .addOnCompleteListener { dialog.dismiss() }
        }


        binding.btSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


}