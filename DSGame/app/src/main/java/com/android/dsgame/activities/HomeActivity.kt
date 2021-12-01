package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.databinding.ActivityHomeBinding
import com.android.dsgame.view.HexButton



class HomeActivity : AppCompatActivity() {
    private val binding = ActivityHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        for (i in board.COLORS.indices) {
            val color = board.COLORS[i]

            val id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            val hexButton = findViewById<HexButton>(id)
            val intent = Intent(this, SelectionActivity::class.java)

            // set action
            hexButton.setOnClickListener {
                if (color == "black" || (board.spots[board.COLORS[i-1]] != null)) {
                    intent.putExtra("color", color)
                    startActivity(intent)
                } else
                    Toast.makeText(this@HomeActivity, "Select a card for the previous position.", Toast.LENGTH_SHORT).show()
            }

            // print card values
            if (board.spots[color] != null)
                hexButton.setCardView(board.spots[color])
            else
                hexButton.setColor(color)
        }

    }

    override fun onResume() {
        super.onResume()
        for (color in board.COLORS) {
            val id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            val hexButton = findViewById<HexButton>(id)

            hexButton.setCardView(board.spots[color])
        }
    }
}
