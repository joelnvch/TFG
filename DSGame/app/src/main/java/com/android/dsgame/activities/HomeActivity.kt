package com.android.dsgame.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.activities.MyApplication.Companion.pyBoard
import com.android.dsgame.databinding.ActivityHomeBinding
import com.android.dsgame.view.HexButton
import com.chaquo.python.PyObject
import com.google.firebase.firestore.FirebaseFirestore
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, SelectionActivity::class.java)
        for (i in board.COLORS.indices) {
            val color = board.COLORS[i]

            val id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            val hexButton = findViewById<HexButton>(id)

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

        // UPDATE SCORE
        binding.tvScore.text = binding.tvScore.text.toString() + board.score


        // SAVE BOARD
        binding.btSave.setOnClickListener{
            val database = FirebaseFirestore.getInstance()
            // we have to save Board and pyBoard as data files
            // for now we'll save it as is, next changes could be Board.COLORS and allCards
            // initialized in corresponding classes.
            // https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android

            // cant serialize pyObject, this is needed
            val fos: FileOutputStream = this.openFileOutput("filename", Context.MODE_PRIVATE)
            val os = ObjectOutputStream(fos)
            os.writeObject(pyBoard)
            os.close()
            fos.close()


            /*
            here we should save "filename" to DB
            cant save data to firestore firebase, read:
            https://stackoverflow.com/questions/49529957/uploading-and-downloading-files-to-from-google-cloud-firestore
            https://firebase.google.com/docs/storage/android/upload-files

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
             
             instead of saving and retrieving, we could create a new pyBoard based on board
             */

            // to recover data try this
            val fis: FileInputStream = this.openFileInput("fileName")
            val iss = ObjectInputStream(fis)
            pyBoard = iss.readObject() as PyObject
            iss.close()
            fis.close()



            // why yesterday board save failed (crashes when null value present):
            //https://stackoverflow.com/questions/47560647/firebase-save-object-with-map-property
            database.collection("board_history").document("LA") // boardHistoryRef
                .set(board.spots)
                .addOnSuccessListener {
                    Toast.makeText(this@HomeActivity,
                        "Board succesfully saved.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    AlertDialog.Builder(this).apply {
                        setMessage("Check your connection or try again.")
                        setPositiveButton(
                            "OK",
                            null
                        )
                    }.show()
                }

            database.collection("a").document("la").set("a")
        }

        // BOARD HISTORY
        binding.btHistory.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }

}
