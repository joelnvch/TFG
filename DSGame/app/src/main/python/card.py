COLORS = ["black", "blue", "yellow", "red", "green", "purple", "orange"]

class Card:
    def __init__(self, id, name, costs, letters, value, card_color):
        self.id = id
        self.name = name
        self.costs = costs
        self.letters = letters
        self.value = value
        self.card_color = card_color

def transform_card(kt_card):
    if kt_card is not None:
        costs = {}
        letters = []

        for color in COLORS:
            costs[color] = kt_card.getCosts().get(color)

        for letter in kt_card.getLetters().toArray():
            letters.append(letter)

        return Card(kt_card.getId(), kt_card.getName(), costs, letters, kt_card.getValue(), kt_card.getCardColor())