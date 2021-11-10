from collections import Counter
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

    def init_board(self, all_cards, board_cards):
        """Initialize board value"""
        self.spots = dict.fromkeys(COLORS)
        self.all_cards = all_cards
        self.next_empty_pos = len(board_cards)

        for i in range(len(board_cards)):
            self.spots[COLORS[i]] = board_cards[i]
            self.board_letters.extend(board_cards[i].letters)
            self.board_costs = dict(Counter(self.board_costs) + Counter(board_cards[i].costs))

        self.board_value = self.calculate_board_value()

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
                current_value = self.calculate_board_value(card)
                # compare values and save best card
                if current_value > highest_val:
                    best_card = card
                    highest_val = current_value
        elif mode == 1:
            lowest_cost = 100
            for card in self.all_cards[color]:
                current_cost = sum((Counter(self.board_costs) + Counter(card.costs)).values())
                if current_cost < lowest_cost:
                    best_card = card
                    lowest_cost = current_cost
        elif mode == 2:
            high_score = -100
            for card in self.all_cards[color]:
                current_score = self.calculate_board_value(card) - sum(
                    (Counter(self.board_costs) + Counter(card.costs)).values())
                if current_score > high_score:
                    best_card = card
                    high_score = current_score

        return best_card

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

    def set_card(self, card):
        if self.next_empty_pos == -1:
            raise Exception('Tablero lleno')

        self.spots[COLORS[self.next_empty_pos]] = card
        self.board_letters.extend(card.letters)

        self.board_costs = dict(Counter(self.board_costs) + Counter(card.costs))

        if self.next_empty_pos == len(COLORS) - 1:
            self.next_empty_pos = -1
        else:
            self.next_empty_pos += 1

        self.board_value = self.calculate_board_value()

    def build_board(self, mode):
        calculated_cards = []
        while self.next_empty_pos != -1:
            calculated_cards.append(self.next_best_move(mode))

        return calculated_cards

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

    def __str__(self):
        val = ""

        for card in self.spots.values():
            if card is None:
                break
            val += str(card)

        return val
