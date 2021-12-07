package com.android.dsgame.activities

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.databinding.ActivityHomeBinding
import com.android.dsgame.managers.ConnectionManager
import com.android.dsgame.managers.GameManager.COLORS
import com.android.dsgame.view.CardElement
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var connectionManager = ConnectionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SET NAME BOARD VALUE
        binding.etBoardName.setText(board.name)

        for (i in COLORS.indices) {
            val intent = Intent(this, SelectionActivity::class.java)
            val color = COLORS[i]

            val id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            val cardElement = findViewById<CardElement>(id)

            // LINK WITH SELECTION ACTIVITY
            cardElement.setOnClickListener {
                if (color == "black" || (board.spots[COLORS[i-1]] != null)) {
                    board.name = binding.etBoardName.text.toString() // save board name
                    intent.putExtra("color", color)
                    startActivity(intent)
                } else
                    Toast.makeText(this@HomeActivity, "Select a card for the previous position.", Toast.LENGTH_SHORT).show()
            }

            // UPDATE VISUAL ELEMENTS
            if (board.spots[color] != null)
                cardElement.setCardView(board.spots[color])
            else
                cardElement.setColor(color)
        }


        // UPDATE SCORE
        binding.tvScore.text = binding.tvScore.text.toString() + board.score



        // SAVE BOARD
        binding.btSave.setOnClickListener{
            board.name = binding.etBoardName.text.toString()
            board.date = Calendar.getInstance().time

            connectionManager.database.collection("Boards")
                .whereEqualTo("userId", connectionManager.authenticator.currentUser!!.uid)
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener {queryResult ->
                    if (queryResult.size() >= 10)
                        connectionManager.database.collection("Boards").document(queryResult.documents[0].id)
                            .delete()
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }




            connectionManager.database.collection("Boards") // boardHistoryRef
                .add(board)
                .addOnSuccessListener {
                    Log.d(TAG, "Element saved.")
                    Toast.makeText(
                        this,
                        "Board succesfully saved.", Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error saving.")
                    AlertDialog.Builder(this).apply {
                        setMessage("Error saving board. \nCheck your connection or try again.")
                        setPositiveButton(
                            "OK",
                            null
                        )
                    }.show()
                }
        }


        // BOARD HISTORY
        binding.btHistory.setOnClickListener{
            startActivity(Intent(this, HistoryActivity::class.java))
        }


        // LOG OUT
        binding.btLogout.setOnClickListener{
            connectionManager.authenticator.signOut()
            var intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


}
