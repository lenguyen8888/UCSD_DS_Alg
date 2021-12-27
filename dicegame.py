## question 1
from itertools import product

def count_wins(dice1, dice2):
    assert len(dice1) == 6 and len(dice2) == 6
    dice1_wins, dice2_wins = 0, 0
    
    # write your code here
    for dVal in product(dice1, dice2):
        #print(dVal)
        if dVal[0] > dVal[1]:
            dice1_wins += 1
        elif dVal[1] > dVal[0]:
            dice2_wins += 1

    return (dice1_wins, dice2_wins)

## question 2
def find_the_best_dice(dices):
    assert all(len(dice) == 6 for dice in dices)

    # write your code here
    # use your implementation of count_wins method if necessary
    bestDice = -1
    for i in range(0,len(dices)):
        isBetter = True
        for j in range(len(dices)):
            if isBetter and i != j:
                (i_win, j_win) = count_wins(dices[i], dices[j])
                isBetter = i_win > j_win
        if isBetter:
            return i
    return -1

## question 3
def compute_strategy(dices):
    assert all(len(dice) == 6 for dice in dices)


    #strategy = dict()
    #strategy["choose_first"] = True
    #strategy["first_dice"] = 0
    #for i in range(len(dices)):
        #strategy[i] = (i + 1) % len(dices)
        
    # write your code here
    bestDice = find_the_best_dice(dices)
    strategy = dict()
    strategy["choose_first"] = bestDice != -1
    strategy["first_dice"] = bestDice
    if bestDice != -1:
        return strategy
    numDices = len(dices)
    for i in range(numDices):
        win_diff = 0
        for j in range(numDices):
            (i_win, j_win) = count_wins(dices[i], dices[j])
            if win_diff < (j_win - i_win):
                strategy[i] = j
                win_diff = j_win - i_win

    return strategy

