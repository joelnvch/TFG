package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.managers.DatabaseManager
import com.google.firebase.auth.FirebaseAuth

class LauncherActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authenticator.currentUser != null) {
            // User is signed in
            var intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        } else{
            // not signed in
            var intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}