# Uses python3
import sys
DEBUG = False
# def get_change(m):
#     #write your code here
#     return m // 4

# write a dynamic programming solution for get_change on 
# the coins [1, 3, 4]
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
    >>> get_change(34)
    9
    
    """
    coins = [1, 3, 4]
    # initialize min_num_coins
    min_num_coins = [0] + [float('inf')] * m
    for i in range(1, m + 1):
        for coin in coins:
            if i >= coin:
                num_coins = min_num_coins[i - coin] + 1
                if num_coins < min_num_coins[i]:
                    min_num_coins[i] = num_coins
    return min_num_coins[m]

# write test_case
def test_case():
    """
    Some test cases for get_change function.

    Parameters:
    """
    assert get_change(2) == 2
    assert get_change(34) == 9
    print("Test passed...")
    return


if __name__ == '__main__':
    if DEBUG:
        test_case()
    m = int(sys.stdin.read())
    print(get_change(m))
