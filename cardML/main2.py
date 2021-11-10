from card import Card
from board import Board
import os
import json

COLORS = ["black", "blue", "yellow", "red", "green", "purple", "orange"]


def init_board(board, path_all_cards):
    """Initialize board value"""
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
    board.all_cards = cards
    board.spots = dict.fromkeys(COLORS)
    board.next_empty_pos = 0
    board.board_value = board.calculate_board_value()
    board.board_letters = []
    board.board_costs = {}
