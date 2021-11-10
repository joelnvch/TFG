
  
class Board:
  def __init__:
    
  
  def get_best_cards(board, color):
    best_moves = []
    color = COLORS[board.next_empty_pos]

    best_moves.append(board.calculate_best_card(color, 0))
    best_moves.append(board.calculate_best_card(color, 1))
    best_moves.append(board.calculate_best_card(color, 2))

    return best_moves
  
  
  # get_best_cards: done in local
  # set_card: tweak
  def set_card(self, color, card_id):
    # validate pos
    if COLORS.index(color) == self.next_empty_pos:
      if self.next_empty_pos == len(COLORS) - 1:
        self.next_empty_pos = -1
      else
        self.next_empty_pos += 1
        
    else if self.next_empty_pos != -1
      raise Exception('Posicion incorrecta')
      
    # set atrs  (get local changes instead of this part)
    card = self.all_cards[color][card_id] 
    
    self.spots[COLORS[self.next_empty_pos]] = card
    self.letters_rep += Counter(card.letters)
    self.board_costs += Counter(card.costs)

    self.board_value = self.__calculate_board_value()
