import os
import json
from card import Card

COLORS = ["black", "blue", "yellow", "red", "green", "purple", "orange"]

class Board:
    def __init__(self, all_cards=None, spots=None, next_empty_pos=0, board_value=0,
                     board_letters=[], board_costs={}):
        self.all_cards = all_cards
        self.spots = spots

        self.next_empty_pos = next_empty_pos
        self.board_value = board_value
        self.board_letters = board_letters
        self.board_costs = board_costs


    def get_best_cards(self, color):
        """Calculates best card of a color depending on board state"""
        best_card = None
        best_cards = []

        highest_val = 0
        for card in self.all_cards[color].values():
            # calculate the value of the board with that card
            current_value = self.calculate_board_value(card)
            # compare values and save best card
            if current_value > highest_val:
                best_card = card
                highest_val = current_value
        best_cards.append(best_card)
        best_card = None

        lowest_cost = 100
        for card in self.all_cards[color].values():
            current_cost = sum((Counter(self.board_costs) + Counter(card.costs)).values())
            if current_cost < lowest_cost:
                best_card = card
                lowest_cost = current_cost
        best_cards.append(best_card)
        best_card = None

        high_score = -100
        for card in self.all_cards[color].values():
            current_score = self.calculate_board_value(card) - sum(
                (Counter(self.board_costs) + Counter(card.costs)).values())
            if current_score > high_score:
                best_card = card
                high_score = current_score
        best_cards.append(best_card)

        return best_cards


    def calculate_board_value(self, test_card=None):
        value = 0

        for card in self.spots.values():
            if card is None:
                break
            value += card.value

        if test_card is not None:
            letters_rep = Counter(self.board_letters) + Counter(test_card.letters)
            value += test_card.value
        else:
            letters_rep = Counter(self.board_letters)

        for rep_number in letters_rep.values():
            if rep_number > 1:
                value += rep_number - 1

        return value


def init_board(path_all_cards):
    """Initialize board value"""
    path_all_cards = os.path.join(os.path.dirname(__file__), path_all_cards)

    # load cards
    cards = dict.fromkeys(COLORS)
    cont = 0
    for filename in os.listdir(path_all_cards):
        if filename.endswith(".json"):
            json_name = os.path.join(path_all_cards, filename)
            with open(json_name, 'r', encoding="utf8") as card_file:
                cards[COLORS[cont]] = []
                card_dict = {}
                id = 0

                card_data = json.loads(card_file.read())
                for card in card_data:
                    id+=1
                    card_dict[id] = Card(id, card['name'], card['costs'], card['letters'], card['value'], card['card_color'])

                cards[COLORS[cont]] = card_dict
        cont += 1

    # set board vals
    all_cards = cards
    spots = dict.fromkeys(COLORS)
    next_empty_pos = 0
    board_value = -1
    board_letters = []
    board_costs = {}

    board = Board(all_cards, spots, next_empty_pos, board_value, board_letters, board_costs)

    return board