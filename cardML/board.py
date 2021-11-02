from collections import Counter

from card import Card

COLORS = ["black", "blue", "yellow", "red", "green", "purple", "orange"]


class Board:
    def __init__(self):
        self.all_cards = None
        self.spots = dict.fromkeys(COLORS)

        self.next_empty_pos = 0
        self.board_value = 0
        self.letters_rep = Counter()
        self.board_costs = Counter()  # initialize dict with 0s

    def init_board(self, all_cards, board_cards):
        """Initialize board value"""
        self.all_cards = all_cards
        self.next_empty_pos = len(board_cards)

        for i in range(len(board_cards)):
            self.spots[COLORS[i]] = board_cards[i]
            self.letters_rep += Counter(board_cards[i].letters)
            self.board_costs += Counter(board_cards[i].costs)

        self.board_value = self.__calculate_board_value()

    def next_best_move(self, mode):
        if self.next_empty_pos != -1:
            color = COLORS[self.next_empty_pos]
            best_card = self.calculate_best_card(color, mode)
            self.set_card(best_card)
        return best_card

    def calculate_best_card(self, color, mode):
        """Calculates best card of a color depending on board state"""
        best_card = None

        if mode == 0:
            highest_val = 0
            for card in self.all_cards[color]:
                # calculate the value of the board with that card
                current_value = self.__calculate_board_value(card)
                # compare values and save best card
                if current_value > highest_val:
                    best_card = card
                    highest_val = current_value
        elif mode == 1:
            lowest_cost = 100
            for card in self.all_cards[color]:
                current_cost = sum((self.board_costs + Counter(card.costs)).values())
                if current_cost < lowest_cost:
                    best_card = card
                    lowest_cost = current_cost
        elif mode == 2:
            high_score = -100
            for card in self.all_cards[color]:
                current_score = self.__calculate_board_value(card) - sum(
                    (self.board_costs + Counter(card.costs)).values())
                if current_score > high_score:
                    best_card = card
                    high_score = current_score

        return best_card

    def set_card(self, card):
        if self.next_empty_pos == -1:
            raise Exception('Tablero lleno')

        self.spots[COLORS[self.next_empty_pos]] = card
        self.letters_rep += Counter(card.letters)
        self.board_costs += Counter(card.costs)

        if self.next_empty_pos == len(COLORS) - 1:
            self.next_empty_pos = -1
        else:
            self.next_empty_pos += 1

        self.board_value = self.__calculate_board_value()

    def build_board(self, mode):
        calculated_cards = []
        while self.next_empty_pos != -1:
            calculated_cards.append(self.next_best_move(mode))

        return calculated_cards

    def __calculate_board_value(self, test_card=None):
        value = 0

        for card in self.spots.values():
            if card is None:
                break
            value += card.value

        if test_card is not None:
            letters_rep = self.letters_rep + Counter(test_card.letters)
            value += test_card.value
        else:
            letters_rep = self.letters_rep

        for rep_number in letters_rep.values():
            if rep_number > 1:
                value += rep_number - 1

        return value

    def __str__(self):
        val = ""

        for card in self.spots.values():
            if card is None:
                break
            val += str(card)

        return val
