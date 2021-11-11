package src.utils;

public class GameUtil {
    //pyBoard.callAttr("get_best_cards", "black")
    /*
    fun getBestCards(color: String): MutableList<CardView> {
        var pythonFile = Python.getInstance().getModule("board")

        val pyBestCards = pythonFile.callAttr("get_best_cards", color).asList()
        return transformPyCardList(pyBestCards)
    }

    // better this than to call python and iterate list multiple times when it can be done just once when object initialization
    fun getAllCards(color: String): MutableList<CardView> {
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
    fun transformPyCardList(pyCards: List<PyObject>): MutableList<CardView>{
        val cards = mutableListOf<CardView>()

        for (pyCard in pyCards)
            cards.add(CardView(pyCard))

        return cards
    }    */
}
