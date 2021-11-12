import json
import os


def load_cards(path):
    keys = ["black", "blue", "yellow", "red", "green", "purple", "orange"]
    cards = dict.fromkeys(keys)
    cont = 0
    for filename in os.listdir(path):
        if filename.endswith(".json"):
            json_name = os.path.join(path, filename)
            with open(json_name, 'r', encoding="utf8") as card_file:
                cards[keys[cont]] = []
                card_data = json.loads(card_file.read())
                for card in card_data:
                    cards[keys[cont]].append(Card(**card))
        cont += 1

    return cards


def get_card(name, list):
    for card in list:
        if card.name == name:
            return card


class Card:
    def __init__(self, id, name, costs, letters, value, card_color):
        self.id = id
        self.name = name
        self.costs = costs
        self.letters = letters
        self.value = value
        self.card_color = card_color

    def __lt__(self, other):
        return self.value >= other.value

    def __str__(self):
        return "\nName: " + self.name + \
               "\nCosts: " + str(self.costs) + \
               "\nLetters: " + str(self.letters) + \
               "\nValue: " + str(self.value) + \
               "\nCard Color: " + self.card_color
