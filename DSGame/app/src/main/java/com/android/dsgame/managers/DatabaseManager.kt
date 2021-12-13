package com.android.dsgame.managers

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.dsgame.R
import com.android.dsgame.model.Board
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class DatabaseManager(var context: Context) {
    val database = FirebaseFirestore.getInstance()

    interface BoardCallback {
        fun onCallback(boardList:List<Board>)
    }

    fun getBoardsByUID(userId: String, myCallback: BoardCallback){
        val boardList = mutableListOf<Board>()
        val dialog = createLoadingDialog()

        dialog.show()
        database.collection("Boards")
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { queryResults ->
                for (board in queryResults) {
                    boardList.add(board.toObject<Board>())
                }
                myCallback.onCallback(boardList)
            }
            .addOnFailureListener {
                AlertDialog.Builder(context).apply {
                    setMessage("Check your connection or try again.")
                    setPositiveButton(
                        "OK",
                        null
                    )
                }.show()
            }
            .addOnCompleteListener { dialog.dismiss() }
    }

    fun saveBoard(board: Board) {
        database.collection("Boards")
            .whereEqualTo("userId", board.userId)
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { queryResult ->
                if (queryResult.size() > 10)
                    database.collection("Boards").document(queryResult.documents[0].id)
                        .delete()
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted") }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
            }

        database.collection("Boards")
            .add(board)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Board saved.")
                Toast.makeText(
                    context,
                    "Board succesfully saved", Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "Error saving.")
                AlertDialog.Builder(context).apply {
                    setMessage("Error saving board. \nCheck your connection or try again.")
                    setPositiveButton(
                        "OK",
                        null
                    )
                }.show()
            }
    }

    fun createLoadingDialog(): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        return builder.create()
    }
}