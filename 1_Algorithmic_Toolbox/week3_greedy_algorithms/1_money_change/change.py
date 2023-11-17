# Uses python3
import sys

def get_change(m):
    """
    Calculates the minimum number of coins needed to make change for a given amount.

    Parameters:
    m (int): The amount for which change needs to be made.

    Returns:
    int: The minimum number of coins needed to make change for the given amount.

    >>> get_change(2)
    2
    >>> get_change(28)
    6
    >>> get_change(0)
    0
    """
    
    COINS = [10, 5, 1]
    # sort coins in descending order for greedy algorithm
    COINS.sort(reverse=True)

    # initialize number of coins
    num_coins = 0
    # iterate through COINS with coin value c
    for c in COINS:
        # if remaining m is greater than or equal to c
        if m >= c:
            # add number of coins to num_coins
            num_coins += m // c
            # update remaining m
            m = m % c
        # if remaining m is 0, break
        if m == 0:
            break
    return num_coins

if __name__ == '__main__':
    m = int(sys.stdin.read())
    print(get_change(m))
