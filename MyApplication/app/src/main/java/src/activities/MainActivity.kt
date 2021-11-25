package src.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chaquo.python.Python
import src.MyApplication.Companion.PACKAGE_NAME
import src.MyApplication.Companion.board
import src.MyApplication.Companion.pyBoard
import src.R
import src.model.Card
import src.utils.GameDelegate
import view.HexButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        for (i in board.COLORS.indices) {
            var color = board.COLORS[i]

            var id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            var hexButton = findViewById<HexButton>(id)
            var intent = Intent(this, MainActivity2::class.java)

            // set action
            hexButton.setOnClickListener {
                if (color == "black" || (board.spots[board.COLORS[i-1]] != null)) {
                    intent.putExtra("color", color)
                    startActivity(intent)
                } else
                    Toast.makeText(this@MainActivity, "Select a card for the previous position.", Toast.LENGTH_SHORT).show()
            }

            // print card values
            if (board.spots[color] != null)
                hexButton.setCardView(board.spots[color])
            else
                hexButton.setColor(color)
        }

    }

    override fun onResume() {
        super.onResume()
        for (color in board.COLORS) {
            var id = resources.getIdentifier("hb_$color", "id", PACKAGE_NAME)
            var hexButton = findViewById<HexButton>(id)

            hexButton.setCardView(board.spots[color])
        }

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
        var card: Card?

        if (spots != null)
            for (item in spots.asMap()) {
                card = Card(item.value)
                string = item.key.toString()
            }

        pythonFile.callAttr("modifBoard", board)

        //val board = pythonFile.callAttr("init_board", "cardData").toJava(Board::class.java)


        // cambiarCarta(board, carta)
        return "a"
    }



}
