package src.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import view.BoardView
import com.chaquo.python.Python
import view.CardView
import kotlinx.android.synthetic.main.activity_main.*
import src.R

//global variables
import src.MyApplication.Companion.pyBoard
import src.MyApplication.Companion.board


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val button = findViewById<Button>(R.id.button)
        //val textView = findViewById<TextView>(R.id.textView)

        //init
        button.setOnClickListener{
            Toast.makeText(this, "pu", Toast.LENGTH_SHORT).show()
            //pyFuncBestCards(color) : List<Card>
            //pyFuncCards(color) : List <Card>

            //val card = pythonFile.callAttr("returnObj", "string").toJava(Card::class.java)

            //val intent = Intent(this, MyOtherActivity::class.java)
            //intent.putExtra("color","black")
            //startActivity(intent)
        }


        textView.text = getHelloPython()
    }

    private fun getHelloPython():String{
        val python=Python.getInstance()
        val pythonFile = python.getModule("board")

        //val board = Chat()
        //val card = pythonFile.callAttr("returnObj", "palabra").toJava(Card::class.java)

        //val board = pythonFile.callAttr("returnObj", "cardData").toJava(Card::class.java)

        //val str = pythonFile.callAttr("returnObj", "aa").toString()

        //val map = pythonFile.callAttr("init_board", "cardData").asMap()
        //for (item in map){
        // item.key.toChar()
         //   item.value.toString()
        //}

        var allCards : MutableMap<String, MutableMap<Int, CardView>> = mutableMapOf()

        var map : MutableMap<Int, CardView>
        for (item in pythonFile.callAttr("init_board", "cardData").getValue("all_cards").asMap()){
            map = mutableMapOf()
            for (item2 in item.value.asMap())
                map.put(item2.key.toInt(), CardView(item2.value))
            allCards.put(item.key.toString(), map)
        }

        val board = pythonFile.callAttr("returnBoard").toJava(BoardView::class.java)

        pythonFile.callAttr("modifBoard", board)
        //val board = pythonFile.callAttr("init_board", "cardData").toJava(Board::class.java)


        // cambiarCarta(board, carta)
        return "a"
    }


}
