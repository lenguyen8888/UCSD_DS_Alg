# Uses python3
import sys

DEBUG = False


def optimal_weight(W, w):
    """
    Finds the maximum weight of gold that can be put into a bag of capacity W.

    Args:
        W (int): The capacity of the bag.
        w (list): A list of weights of gold bars.

    Returns:
        int: The maximum weight of gold that can be put into the bag.
    Examples:
        >>> optimal_weight(10, [1, 4, 8])
        9
        >>> optimal_weight(10, [1, 2, 3, 4, 5])
        10
        >>> optimal_weight(10, [5, 4, 3, 2, 1])
        10
    """
    # write dynamic programming solution
    # that find the maximum weight of gold of w's weights
    # that fits into a bag of capacity W
    # initialize the dp table
    dp_table = [[0] * (W + 1) for _ in range(len(w) + 1)]
    # iterate through the dp table
    for i in range(1, len(w) + 1):
        for j in range(1, W + 1):
            # if w[i - 1] <= j:
            if w[i - 1] <= j:
                # dp_table[i][j] = max(dp_table[i - 1][j], dp_table[i - 1][j - w[i - 1]] + w[i - 1])
                dp_table[i][j] = max(dp_table[i - 1][j], dp_table[i - 1][j - w[i - 1]] + w[i - 1])
            # else:
            else:
                # dp_table[i][j] = dp_table[i - 1][j]
                dp_table[i][j] = dp_table[i - 1][j]
    # return the bottom right corner of the dp table
    return dp_table[len(w)][W]
    
# Write test cases
def test_cases():
    assert optimal_weight(10, [1, 4, 8]) == 9
    assert optimal_weight(10, [1, 2, 3, 4, 5]) == 10
    assert optimal_weight(10, [5, 4, 3, 2, 1]) == 10
    print('All test cases passed.')


if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        input = sys.stdin.read()
        W, n, *w = list(map(int, input.split()))
        print(optimal_weight(W, w))
