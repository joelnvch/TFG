package com.android.dsgame.managers;

import com.android.dsgame.activities.MyApplication
import com.chaquo.python.PyObject
import com.android.dsgame.activities.MyApplication.Companion.board
import com.android.dsgame.activities.MyApplication.Companion.pyBoard
import com.android.dsgame.model.Board
import com.android.dsgame.model.Card

object GameManager {
    val ALL_CARDS = mutableMapOf<String, MutableMap<Int, Card>>()
    val COLORS = arrayOf("black", "blue", "yellow", "red", "green", "purple", "orange")

    fun getBestCards(color: String): MutableList<Card> {
        val pyBestCards = pyBoard.callAttr("get_best_cards", color).asList()
        return transformPyCardList(pyBestCards)
    }

    fun getCardsByColor(color: String): MutableList<Card> {
        return ALL_CARDS.getValue(color).values.toMutableList()
    }


    //METHOD CALLED IN THE SECOND SCREEN
    fun setCard(color: String, cardId: Int) {
        // update py object
        pyBoard.callAttr("set_card", color, cardId)

        // update view object
        board.spots[color] = ALL_CARDS.getValue(color).getValue(cardId)
        board.score = pyBoard.getValue("score").toInt()
    }


    fun updateBoard(board: Board){
        MyApplication.board = board
        pyBoard.callAttr("update_board", board)
    }

    // AUX
    fun transformPyCardList(pyCards: List<PyObject>): MutableList<Card>{
        var cards = mutableListOf<Card>()

        for (pyCard in pyCards)
            cards.add(Card(pyCard))

        return cards
    }

}
