import os
import json
from card import Card
from collections import Counter


COLORS = ["black", "blue", "yellow", "red", "green", "purple", "orange"]

class Board:
    def __init__(self, all_cards=None, spots=None):
        self.all_cards = all_cards
        self.spots = spots


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


        board_costs = self.get_letters_and_costs()[1]

        lowest_cost = 100
        for card in self.all_cards[color].values():
            current_cost = sum((Counter(board_costs) + Counter(card.costs)).values())
            if current_cost < lowest_cost:
                best_card = card
                lowest_cost = current_cost
        best_cards.append(best_card)
        best_card = None

        high_score = -100
        for card in self.all_cards[color].values():
            current_score = self.calculate_board_value(card) - sum(
                (Counter(board_costs) + Counter(card.costs)).values())
            if current_score > high_score:
                best_card = card
                high_score = current_score
        best_cards.append(best_card)

        return best_cards


    def calculate_board_value(self, test_card):
        value = 0

        # set temporarily current position to None in case of previous existant val
        old_card = None
        if self.spots[test_card.card_color] is not None:
            old_card = self.spots[test_card.card_color]
            self.spots[test_card.card_color] = None

        for card in self.spots.values():
            if card is None:
                break
            value += card.value

        board_letters = self.get_letters_and_costs()[0]
        if test_card is not None:
            letters_rep = Counter(board_letters) + Counter(test_card.letters)
            value += test_card.value
        else:
            letters_rep = Counter(board_letters)

        for rep_number in letters_rep.values():
            if rep_number > 1:
                value += rep_number - 1

        # reset old value of spot
        if old_card:
            self.spots[test_card.card_color] = old_card
        return value

    def set_card(self, color, card_id):
        card = self.all_cards[color][card_id]
        self.spots[color] = card

    def get_letters_and_costs(self):
        """Iterate over board and count letters"""
        letters_rep = []
        costs = []
        for color in COLORS:
            if self.spots[color] is not None:
                letters_rep += Counter(self.spots[color].letters)
                costs += Counter(self.spots[color].costs)
        return letters_rep, costs


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

    board = Board(all_cards, spots)

    return board
