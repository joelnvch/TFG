package com.android.dsgame.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.dsgame.databinding.ActivityRegisterBinding
import com.android.dsgame.managers.DatabaseManager
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val authenticator = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSignup.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatedPassword = binding.repeatedPassword.text.toString()

            if (!isValidCredentials(email, password, repeatedPassword))
                return@setOnClickListener

            val dbm = DatabaseManager(this)
            val dialog = dbm.createLoadingDialog()
            dialog.show()
            authenticator.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener{
                    authenticator.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    AlertDialog.Builder(this).apply {
                        setTitle("Error signing up")
                        setMessage(it.message)
                        setPositiveButton(
                            "OK",
                            null
                        )
                    }.show()
                }
                .addOnCompleteListener{
                    dialog.dismiss()
                }
        }
    }


    private fun isValidCredentials(email: String, password: String, repeatedPassword: String): Boolean {
        if (email == "" || password == "") {
            AlertDialog.Builder(this).apply {
                setTitle("Error singing up")
                setMessage("Invalid email or password.")
                setPositiveButton(
                    "OK",
                    null
                )
            }.show()
            return false
        }

        if (password != repeatedPassword) {
            AlertDialog.Builder(this).apply {
                setTitle("Error signing up")
                setMessage("Passwords don't match.")
                setPositiveButton(
                    "OK",
                    null
                )
            }.show()
            return false
        }

        return true
    }
}