package com.android.dsgame.model

import com.chaquo.python.PyObject

class Card(){
    var id: Int? = null
    lateinit var name: String
    var costs: MutableMap<String, Int> = mutableMapOf()
    var letters: MutableList<String> = mutableListOf()
    var value: Int = 0
    lateinit var cardColor: String

    constructor(pyObject: PyObject) : this(){
        id = pyObject.getValue("id").toInt()
        name = pyObject.getValue("name").toString()

        for (item in pyObject.getValue("costs").asMap())
            costs.put(item.key.toString(), item.value.toInt())

        for (item in pyObject.getValue("letters").asList())
            letters.add(item.toString())

        value = pyObject.getValue("value").toInt()
        cardColor = pyObject.getValue("card_color").toString()

    }

}

