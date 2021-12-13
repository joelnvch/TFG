package com.android.dsgame.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dsgame.databinding.ActivityHistoryBinding
import com.android.dsgame.managers.DatabaseManager
import com.android.dsgame.model.Board
import com.android.dsgame.view.adaptors.BoardEntryAdaptor
import com.google.firebase.auth.FirebaseAuth


class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private var connectionManager = DatabaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context = this
        val authenticator = FirebaseAuth.getInstance()
        val uid = authenticator.currentUser!!.uid

        connectionManager.getBoardsByUID(uid, object : DatabaseManager.BoardCallback {
            override fun onCallback(boardList: List<Board>) {
                binding.boardList.layoutManager = LinearLayoutManager(context)
                binding.boardList.adapter = BoardEntryAdaptor(boardList.toTypedArray())
            }
        })

    }

}