import sys
import os
import json

from collections import Counter
# from beans import Board

COLORS = ["black", "blue", "yellow", "red", "green", "purple", "orange"]


def returnObj(string):
   return string

def returnMap():
    return dict.fromkeys(['c', 's'])

def returnBoard():
    board = Board()
    return board

def returnCard():
    card = Card("a", dict.fromkeys(['c', 's']), [], 0, "sasd")
    return card

def modifBoard(board):
    board.setNext_empty_pos(100)
