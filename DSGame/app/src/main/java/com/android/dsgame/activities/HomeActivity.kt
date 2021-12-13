package com.android.dsgame.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.databinding.ActivityHomeBinding
import com.android.dsgame.managers.DatabaseManager
import com.android.dsgame.managers.GameManager
import com.android.dsgame.managers.GameManager.COLORS
import com.android.dsgame.view.CardElement
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var connectionManager = DatabaseManager(this)
    private val authenticator = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in COLORS.indices) {
            val color = COLORS[i]
            val id = resources.getIdentifier("ce_$color", "id", PACKAGE_NAME)
            val cardElement = findViewById<CardElement>(id)

            // LINK WITH SELECTION ACTIVITY
            cardElement.setOnClickListener {
                if (color == "black" || (board.spots[COLORS[i-1]] != null)) {
                    board.name = binding.etBoardName.text.toString() // save board name
                    val i = Intent(this, SelectionActivity::class.java)
                    i.putExtra("color", color)
                    startActivity(i)
                } else
                    Toast.makeText(this@HomeActivity, "Select a card in the previous position.", Toast.LENGTH_SHORT).show()
            }

            // UPDATE VISUAL ELEMENTS
            if (board.spots[color] != null)
                cardElement.setCardElement(board.spots[color])
            else
                cardElement.setColor(color)
        }


        // UPDATE SCORE
        binding.tvScore.text = binding.tvScore.text.toString() + board.score
        // SET NAME BOARD VALUE
        binding.etBoardName.setText(board.name)


        // SAVE BOARD
        binding.btSave.setOnClickListener{
            board.name = binding.etBoardName.text.toString()
            board.date = Calendar.getInstance().time
            board.userId = authenticator.currentUser!!.uid

            connectionManager.saveBoard(board)
        }


        // BOARD HISTORY
        binding.btHistory.setOnClickListener{
            startActivity(Intent(this, HistoryActivity::class.java))
        }


        // LOG OUT
        binding.btLogout.setOnClickListener{
            authenticator.signOut()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            GameManager.initBoard()
            startActivity(intent)
        }
    }
}