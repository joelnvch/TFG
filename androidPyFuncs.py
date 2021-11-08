def get_all_cards(board, color):
  return board.all_cards[color]
  
  
def get_best_moves(board, color):
  best_moves = []
  color = COLORS[board.next_empty_pos]
  
  best_moves.append(board.calculate_best_card(color, 0))
  best_moves.append(board.calculate_best_card(color, 1))
  best_moves.append(board.calculate_best_card(color, 2))

  return best_moves
  
# desde deleagate
# getAllCards(board, color) y getBestMoves
# llaman a los métodos de aquí, los convierten a kt class

# añadir a Card.kt -> atr id
# modificar load_cards:
def load_cards(path):
    keys = ["black", "blue", "yellow", "red", "green", "purple", "orange"]
    cards = dict.fromkeys(keys)
    cont = 0
    for filename in os.listdir(path):
        if filename.endswith(".json"):
            json_name = os.path.join(path, filename)
            with open(json_name, 'r', encoding="utf8") as card_file:
                cards[keys[cont]] = []
                card_dict = {}
                id = 0
                
                card_data = json.loads(card_file.read())
                for card in card_data:
                    id += 1
                    card_dict[id] = Card(**card)
                
                cards[keys[cont]] = card_dict
        cont += 1

    return cards



  
  
  
