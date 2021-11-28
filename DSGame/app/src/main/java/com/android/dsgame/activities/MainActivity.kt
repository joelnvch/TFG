package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.databinding.ActivityMainBinding
import com.android.dsgame.view.HexButton


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        for (i in board.COLORS.indices) {
            var color = board.COLORS[i]

            var id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            var hexButton = findViewById<HexButton>(id)
            var intent = Intent(this, MainActivity2::class.java)

            // set action
            hexButton.setOnClickListener {
                if (color == "black" || (board.spots[board.COLORS[i-1]] != null)) {
                    intent.putExtra("color", color)
                    startActivity(intent)
                } else
                    Toast.makeText(this@MainActivity, "Select a card for the previous position.", Toast.LENGTH_SHORT).show()
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
            var id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            var hexButton = findViewById<HexButton>(id)

            hexButton.setCardView(board.spots[color])
        }
    }
}
