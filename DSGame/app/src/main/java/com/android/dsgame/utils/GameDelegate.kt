package com.android.dsgame.utils;

import com.chaquo.python.PyObject
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.activities.MyApplication.Companion.pyBoard
import com.android.dsgame.model.Card

object GameDelegate {
    //pyBoard.callAttr("get_best_cards", "black")

    fun getBestCards(color: String): MutableList<Card> {
        val pyBestCards = pyBoard.callAttr("get_best_cards", color).asList()
        return transformPyCardList(pyBestCards)
    }

    // better this than to call python and iterate list multiple times when it can be done just once when object initialization
    fun getAllCards(color: String): MutableList<Card> {
        return board.allCards.getValue(color).values.toMutableList()
    }


    //METHOD CALLED IN THE SECOND SCREEN
    fun setCard(color: String, cardId: Int) {
        // update logic object
        pyBoard.callAttr("set_card", color, cardId)
        // update view object
        board.spots[color] = board.allCards.getValue(color).getValue(cardId)
    }


    // AUX
    fun transformPyCardList(pyCards: List<PyObject>): MutableList<Card>{
        var cards = mutableListOf<Card>()

        for (pyCard in pyCards)
            cards.add(Card(pyCard))

        return cards
    }

}
