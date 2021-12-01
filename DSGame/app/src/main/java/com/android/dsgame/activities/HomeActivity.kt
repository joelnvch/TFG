package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.databinding.ActivityHomeBinding
import com.android.dsgame.view.HexButton
import com.google.firebase.firestore.FirebaseFirestore


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in board.COLORS.indices) {
            val color = board.COLORS[i]

            val id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            val hexButton = findViewById<HexButton>(id)
            val intent = Intent(this, SelectionActivity::class.java)

            // LINK WITH SELECTION ACTIVITY
            hexButton.setOnClickListener {
                if (color == "black" || (board.spots[board.COLORS[i-1]] != null)) {
                    intent.putExtra("color", color)
                    startActivity(intent)
                } else
                    Toast.makeText(this@HomeActivity, "Select a card for the previous position.", Toast.LENGTH_SHORT).show()
            }

            // UPDATE VISIBLE BOARD
            if (board.spots[color] != null)
                hexButton.setCardView(board.spots[color])
            else
                hexButton.setColor(color)
        }


        // SAVE BOARD
        binding.btSave.setOnClickListener{
            val data = hashMapOf(
                "name" to "Tokyo",
                "country" to "Japan"
            )
            database.collection("board_history").document("LA")
                .set(board.spots)
                .addOnSuccessListener {
                    Toast.makeText(this@HomeActivity,
                        "ok", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@HomeActivity,
                        "error", Toast.LENGTH_SHORT).show()
                }
        }

    }

}
