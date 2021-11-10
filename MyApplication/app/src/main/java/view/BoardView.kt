package view

import com.chaquo.python.PyObject
import src.MyApplication


class BoardView(pyObject: PyObject){
    var allCards: MutableMap<String, MutableMap<Int, CardView>> = mutableMapOf()
    var spots: MutableMap<String, CardView?> = mutableMapOf()


    init{
        var map : MutableMap<Int, CardView>
        for (item in MyApplication.pyBoard.getValue("all_cards").asMap()){
            map = mutableMapOf()
            for (item2 in item.value.asMap())
                map.put(item2.key.toInt(), CardView(item2.value))
            allCards.put(item.key.toString(), map)
        }

        for (item in pyObject.getValue("spots").asMap())
            if (item.value != null)
                this.spots.put(item.key.toString(), item.value.toJava(CardView::class.java))
            else
                this.spots.put(item.key.toString(), null)

    }

}
