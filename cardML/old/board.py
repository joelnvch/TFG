from card import Card
from collections import Counter

COLORS = ["black", "blue", "yellow", "red", "green", "purple", "orange"]


class Board:
    def __init__(self, all_cards):
        self.all_cards = all_cards
        self.spots = dict.fromkeys(COLORS)

        self.next_empty_pos = 0
        self.board_value = 0
        self.letters_rep = Counter()
        self.board_costs = Counter()  # initialize dict with 0s
