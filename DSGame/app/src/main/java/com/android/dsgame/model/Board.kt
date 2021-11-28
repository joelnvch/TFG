package com.android.dsgame.model

import com.chaquo.python.PyObject
import com.android.dsgame.activities.MyApplication


class Board(pyObject: PyObject){
    var allCards: MutableMap<String, MutableMap<Int, Card>> = mutableMapOf()
    var spots: MutableMap<String, Card?> = mutableMapOf()
    val COLORS: Array<String> = arrayOf("black", "blue", "yellow", "red", "green", "purple", "orange")


    init{
        var map : MutableMap<Int, Card>
        for (item in MyApplication.pyBoard.getValue("all_cards").asMap()){
            map = mutableMapOf()
            for (item2 in item.value.asMap())
                map.put(item2.key.toInt(), Card(item2.value))
            allCards.put(item.key.toString(), map)
        }

        for (item in pyObject.getValue("spots").asMap())
            if (item.value != null)
                this.spots.put(item.key.toString(), item.value.toJava(Card::class.java))
            else
                this.spots.put(item.key.toString(), null)

    }

}
