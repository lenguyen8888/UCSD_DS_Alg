# Uses python3
import sys

def get_change(m):
    """
    Calculates the minimum number of coins needed to make change for a given amount.

    Parameters:
    m (int): The amount for which change needs to be made.

    Returns:
    int: The minimum number of coins needed to make change for the given amount.

    Examples:
    >>> get_change(2)
    2
    >>> get_change(28)
    6
    >>> get_change(0)
    0

    This function uses a greedy algorithm to calculate the minimum number of coins needed
    for change. It iterates through a list of coin denominations in descending order and
    repeatedly selects the largest coin that is less than or equal to the remaining amount.
    It then subtracts the selected coin value from the remaining amount and increments the
    count of coins used. This process continues until the remaining amount becomes zero or
    all coins have been considered. The function returns the count of coins used.

    Note that the greedy algorithm may not always produce the optimal solution for all coin
    systems. However, it works correctly for the standard coin system where the coin
    denominations are powers of 10 (e.g., 1, 5, 10, 50, 100).
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
    # read input from standard input
    m = int(sys.stdin.read())
    print(get_change(m))
