package view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import src.MyApplication.Companion.PACKAGE_NAME
import src.R
import src.activities.MainActivity
import src.model.Card

/**
 * TODO: document your custom view class.
 */
class HexButton : FrameLayout {
    var vista: View

    // Constructors
    constructor(context: Context) : super(context) {
        vista = inflate(context, R.layout.boton_hex, this)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        vista = inflate(context, R.layout.boton_hex, this)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        vista = inflate(context, R.layout.boton_hex, this)
    }


    // Setters
    fun setCardView(card: Card?) {
        if (card != null){
            setName(card.name)
            setCosts(card.costs)
            setLetters(card.letters)
            setValue(card.value)
            setColor(card.cardColor)
        }
    }

    fun setName(name: String){
        val nameView = vista.findViewById<TextView>(R.id.tvNombre)
        nameView.text = name
    }

    fun setLetters(letters: MutableList<Char>){
        val letterView = vista.findViewById<TextView>(R.id.tvLetra)
        letterView.text = letters.toString()
    }

    fun setValue(value: Int){
        val valueView = vista.findViewById<TextView>(R.id.tvValor)
        valueView.text = value.toString()
    }

    fun setColor(color: String){
        val image = vista.findViewById<ImageView>(R.id.ivHex)
        var id = resources.getIdentifier("${color}_hexagon", "drawable", PACKAGE_NAME)
        image.setImageResource(id)
    }

    @SuppressLint("ResourceAsColor")
    fun setCosts(costs: MutableMap<String, Int>) {
        var pos = 1

        for (cost in costs) {
            var id = resources.getIdentifier("tvCoste$pos", "id", PACKAGE_NAME)
            var costView = vista.findViewById<TextView>(id)

            costView.text = cost.value.toString()

            if ("orange" == cost.key)
                costView.setBackgroundColor(R.color.orange)
            else
                costView.setBackgroundColor(Color.parseColor(cost.key))

            pos++
        }

        for (i in pos until 6) {
            var id = resources.getIdentifier("tvCoste$i", "id", PACKAGE_NAME)
            var costView = vista.findViewById<TextView>(id)
            costView.visibility = View.GONE
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        val boton = vista.findViewById<Button>(R.id.btHex)
        boton.setOnClickListener(l)
    }
}