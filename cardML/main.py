import card as card

from card import load_cards

# constants
from main2 import *

CARD_DATA_PATH = "cardData"


def sort_by_highest(card_types):
    for i in range(len(COLORS)):
        card_list_by_color = card_types[COLORS[i]]
        card_types[COLORS[i]] = sorted(card_list_by_color)

        sum = 0
        for card in card_list_by_color:
            sum += card.value
            spots = vars(card)


if __name__ == '__main__':
    all_cards = load_cards(CARD_DATA_PATH)
    # sort_by_highest(all_cards)

    board1 = Board()
    board2 = Board()
    board3 = Board()

    # main2
    init_board(board1, CARD_DATA_PATH)
    board1.get_best_cards('black')

    card.get_card("Text Data", all_cards['black'])

    board1.init_board(all_cards, [all_cards['black'][0], all_cards['blue'][0], all_cards['yellow'][0]])
    print(board1)
    board2.init_board(all_cards, [all_cards['black'][0], all_cards['blue'][0], all_cards['yellow'][0]])
    board3.init_board(all_cards, [all_cards['black'][0], all_cards['blue'][0], all_cards['yellow'][0]])

    # 0 : mayor beneficio
    # 1 : menor coste
    # 2 : mayor beneficio con el menor coste
    calculated_cards1 = board1.build_board(0)
    calculated_cards2 = board2.build_board(1)
    calculated_cards3 = board3.build_board(2)

    for card in calculated_cards1:
        print(card)
