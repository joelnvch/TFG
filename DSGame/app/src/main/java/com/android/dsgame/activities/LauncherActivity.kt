package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.managers.ConnectionManager

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var connectionManager = ConnectionManager(this)

        if (connectionManager.authenticator.currentUser != null) {
            // User is signed in
            var intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        } else{
            // not signed in
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}