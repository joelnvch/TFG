package com.android.dsgame.model

import com.android.dsgame.activities.MyApplication
import com.android.dsgame.activities.MyApplication.Companion.pyBoard
import com.chaquo.python.PyObject
import java.util.*


class Board(){
    var name: String = "My Board"
    var spots: MutableMap<String, Card?> = mutableMapOf()
    var score: Int = 0

    var userId: String = ""
    var date: Date = Calendar.getInstance().time


    constructor(pyObject: PyObject) : this() {
        for (item in pyObject.getValue("spots").asMap())
            if (item.value != null)
                this.spots[item.key.toString()] = Card(item.value)
            else
                this.spots[item.key.toString()] = null
    }

}
