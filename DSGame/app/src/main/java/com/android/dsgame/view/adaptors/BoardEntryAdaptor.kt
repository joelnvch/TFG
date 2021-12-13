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
import java.text.DateFormat
import java.text.SimpleDateFormat


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
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")

        holder.name.text = board.name
        holder.date.text = formatter.format(board.date)
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

                    val intent = Intent(it.context, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    it.context.startActivity(intent)
                }
            }.show()
        }
    }

    override fun getItemCount()  = dataSet.size
}

