package com.android.dsgame.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_history)

        /*
         recover DB data and print in recycler
         search for entries with document_id = current user uid
         iterate results, transform each result to HistoryEntry object
            *HistoryEntry
                -Date
                -Name
                -Board
         */

    }
}