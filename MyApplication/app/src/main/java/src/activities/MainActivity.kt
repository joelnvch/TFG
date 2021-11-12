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
import src.utils.GameDelegate


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

        val bestCards = GameDelegate.getBestCards("black")
        val getAllCards = GameDelegate.getAllCards("black")
        GameDelegate.setCard("black", 1)
        GameDelegate.setCard("blue", 1)
        GameDelegate.setCard("yellow", 1)
        GameDelegate.setCard("blue", 2)

        var spots = pyBoard.get("spots")
        var string: String
        var card: CardView?

        if (spots != null)
            for (item in spots.asMap()) {
                card = CardView(item.value)
                string = item.key.toString()
            }

        pythonFile.callAttr("modifBoard", board)

        //val board = pythonFile.callAttr("init_board", "cardData").toJava(Board::class.java)


        // cambiarCarta(board, carta)
        return "a"
    }


}
