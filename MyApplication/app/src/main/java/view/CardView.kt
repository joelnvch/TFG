package view

import com.chaquo.python.PyObject

class CardView(pyObject: PyObject){
    var id: Int
    var name: String
    var costs: MutableMap<String, Int> = mutableMapOf()
    var letters: MutableList<Char> = mutableListOf()
    var value: Int
    var cardColor: String

    init{
        id = pyObject.getValue("id").toInt()
        name = pyObject.getValue("name").toString()

        for (item in pyObject.getValue("costs").asMap())
            costs.put(item.key.toString(), item.value.toInt())

        for (item in pyObject.getValue("letters").asList())
            letters.add(item.toChar())

        value = pyObject.getValue("value").toInt()
        cardColor = pyObject.getValue("card_color").toString()
    }

}


    /*
    var name: String,
    var costs: PyObject,    //Map<Char, Int>
    var letters: PyObject, //List<Char>,
    var value: Int,
    var card_color: String
)
*/

