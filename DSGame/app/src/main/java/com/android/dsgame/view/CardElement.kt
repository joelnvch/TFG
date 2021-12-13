package com.android.dsgame.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.dsgame.activities.MyApplication.Companion.PACKAGE_NAME
import com.android.dsgame.R
import com.android.dsgame.model.Card


class CardElement : FrameLayout {
    var view: View

    // Constructors
    constructor(context: Context) : super(context) {
        view = inflate(context, R.layout.layout_card_element, this)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        view = inflate(context, R.layout.layout_card_element, this)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        view = inflate(context, R.layout.layout_card_element, this)
    }


    // Setters
    fun setCardElement(card: Card?) {
        if (card != null){
            setName(card.name)
            setCosts(card.costs)
            setLetters(card.letters)
            setValue(card.value)
            setColor(card.cardColor)
        }
    }

    //setcardelement otro con redireccion incluida


    private fun setName(name: String){
        val nameView = view.findViewById<TextView>(R.id.tvCardName)
        nameView.text = name
    }

    fun setLetters(letters: MutableList<String>){
        val letterView = view.findViewById<TextView>(R.id.tvCardLetters)
        letterView.text = letters.toString()
    }

    fun setValue(value: Int){
        val valueView = view.findViewById<TextView>(R.id.tvCardValue)
        valueView.text = value.toString()
    }

    fun setColor(color: String){
        val image = view.findViewById<ImageView>(R.id.ivCardColor)
        val id = resources.getIdentifier("${color}_hexagon", "drawable", PACKAGE_NAME)
        image.setImageResource(id)
    }

    fun setCosts(costs: MutableMap<String, Int>) {
        var pos = 1

        for (cost in costs) {
            val id = resources.getIdentifier("tvCost$pos", "id", PACKAGE_NAME)
            val costView = view.findViewById<TextView>(id)

            costView.text = cost.value.toString()

            val colorId = resources.getIdentifier(cost.key, "color", PACKAGE_NAME)
            val color = ContextCompat.getColor(context, colorId)
            costView.setBackgroundColor(color)

            pos++
        }

        for (i in pos until 6) {
            val id = resources.getIdentifier("tvCost$i", "id", PACKAGE_NAME)
            val costView = view.findViewById<TextView>(id)
            costView.visibility = View.GONE
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        val boton = view.findViewById<Button>(R.id.btCard)
        boton.setOnClickListener(l)
    }
}