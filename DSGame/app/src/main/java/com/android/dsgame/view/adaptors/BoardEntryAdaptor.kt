package com.android.dsgame.view.adaptors

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.dsgame.R
import com.android.dsgame.activities.HomeActivity
import com.android.dsgame.managers.GameManager
import com.android.dsgame.model.Board


class BoardEntryAdaptor (private val dataSet: Array<Board>) :
    RecyclerView.Adapter<BoardEntryAdaptor.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var name: TextView
        var date: TextView
        var score: TextView
        var entryElement: ConstraintLayout

        init {
            name = view.findViewById(R.id.tvName)
            date = view.findViewById(R.id.tvDate)
            score = view.findViewById(R.id.tvScore)
            entryElement = view.findViewById(R.id.entryElement)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_board_entry, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val board = dataSet[position]

        holder.name.text = board.name
        holder.date.text = board.date.day.toString() + "/" + board.date.month.toString() + "/" + board.date.year.toString()
        holder.score.text = board.score.toString()

        holder.entryElement.setOnClickListener{
            AlertDialog.Builder(it.context).apply {
                setMessage("Selected board will be loaded in your current session. " +
                        "\nUnsaved changes will be lost, are you sure?")

                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }

                .setPositiveButton(
                    "OK"
                ) { _, _ ->
                    GameManager.updateBoard(board)
                    it.context.startActivity(Intent(it.context, HomeActivity::class.java))
                }
            }.show()

        }
    }

    override fun getItemCount()  = dataSet.size
}

