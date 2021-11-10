// class GameDelegate (?)

// METHODS CALLED IN THE FIRST SCREEN 
//this screen should have the color variable passed by the first screen (intent)

fun getBestCards(color: String): MutableList {
  val pyBestCards = pythonFile.calltAttr("get_best_cards", color).asList()
  return transformPyCardList(pyBestCards)
}

// better this than to call python and iterate list multiple times when it can be done just once when object initialization
fun getAllCards(color: String): MutableList {
  return all_cards.getValue(color).values()
}


//METHOD CALLED IN THE SECOND SCREEN
fun setCard(color: String, cardId: Int): Int {
  // update logic object
  pyBoard.callAttr("set_card", color, cardId)
  // update view object
  boardView.spots[color] = boardView.allCards.getValue(color).getValue(cardId)
}


// AUX
fun transformPyCardList(pyCards: List<PyObject>): MutableList{
  val cards = mutableListOf<Card>()
  
  for (pyCard in pyCards)
    cards.add(Card(pyCard))
    
  return cards
}

