package com.android.dsgame.managers

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.android.dsgame.R
import com.android.dsgame.model.Board
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class ConnectionManager(var context: Context) {
    val authenticator = FirebaseAuth.getInstance()
    val database = FirebaseFirestore.getInstance()

    interface BoardCallback {
        fun onCallback(boardList:List<Board>)
    }

    fun getBoardsByUID(userId: String, myCallback: BoardCallback){
        val boardList = mutableListOf<Board>()
        val dialog = createDialog(context)

        dialog.show();
        database.collection("Boards")
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { queryResults ->
                for (board in queryResults) {
                    boardList.add(board.toObject<Board>())
                }
                myCallback.onCallback(boardList)
                dialog.dismiss();
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
    }

    private fun createDialog(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        return builder.create()
    }
}