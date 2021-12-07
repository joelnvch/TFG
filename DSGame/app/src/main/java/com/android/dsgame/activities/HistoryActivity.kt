package com.android.dsgame.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dsgame.databinding.ActivityHistoryBinding
import com.android.dsgame.managers.ConnectionManager
import com.android.dsgame.model.Board
import com.android.dsgame.view.adaptors.BoardEntryAdaptor


class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private var connectionManager = ConnectionManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this
        val uid = connectionManager.authenticator.currentUser!!.uid
        connectionManager.getBoardsByUID(uid,
            object : ConnectionManager.BoardCallback {
                override fun onCallback(boardList: List<Board>) {
                    binding.boardList.layoutManager = LinearLayoutManager(context)
                    binding.boardList.adapter = BoardEntryAdaptor(boardList.toTypedArray())
                }
            })

    }

}