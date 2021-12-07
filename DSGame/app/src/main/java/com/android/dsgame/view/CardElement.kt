package com.android.dsgame.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.R
import com.android.dsgame.model.Card

/**
 * TODO: document your custom view class.
 */
class CardElement : FrameLayout {
    var view: View

    // Constructors
    constructor(context: Context) : super(context) {
        view = inflate(context, R.layout.layout_card, this)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        view = inflate(context, R.layout.layout_card, this)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        view = inflate(context, R.layout.layout_card, this)
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
        val nameView = view.findViewById<TextView>(R.id.tvNombre)
        nameView.text = name
    }

    fun setLetters(letters: MutableList<String>){
        val letterView = view.findViewById<TextView>(R.id.tvLetra)
        letterView.text = letters.toString()
    }

    fun setValue(value: Int){
        val valueView = view.findViewById<TextView>(R.id.tvValor)
        valueView.text = value.toString()
    }

    fun setColor(color: String){
        val image = view.findViewById<ImageView>(R.id.ivHex)
        var id = resources.getIdentifier("${color}_hexagon", "drawable", PACKAGE_NAME)
        image.setImageResource(id)
    }

    @SuppressLint("ResourceAsColor")
    fun setCosts(costs: MutableMap<String, Int>) {
        var pos = 1

        for (cost in costs) {
            var id = resources.getIdentifier("tvCoste$pos", "id", PACKAGE_NAME)
            var costView = view.findViewById<TextView>(id)

            costView.text = cost.value.toString()

            if ("orange" == cost.key)
                costView.setBackgroundColor(R.color.orange)
            else
                costView.setBackgroundColor(Color.parseColor(cost.key))

            pos++
        }

        for (i in pos until 6) {
            var id = resources.getIdentifier("tvCoste$i", "id", PACKAGE_NAME)
            var costView = view.findViewById<TextView>(id)
            costView.visibility = View.GONE
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        val boton = view.findViewById<Button>(R.id.btHex)
        boton.setOnClickListener(l)
    }
}