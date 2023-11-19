# Uses python3
import sys
DEBUG = False


def optimal_summands(n):
    """
    Returns a list of distinct summands that add up to the given number 'n'.

    Parameters:
    n (int): The number to be represented as the sum of distinct summands.

    Returns:
    list: A list of distinct summands.

    Example:
    >>> optimal_summands(1)
    [1]
    >>> optimal_summands(2)
    [2]
    >>> optimal_summands(5)
    [1, 4]
    >>> optimal_summands(8)
    [1, 2, 5]
    """
    summands = []
    # The most natural distint summands are 1, 2, 3, ..., k with the last
    # summand being n - k(k+1)/2
    # find the number of summands from 1 to k such that k(k+1)/2 <= n
    # k^2 + k - 2n <= 0
    # k = (-1 + sqrt(1 + 8n)) / 2
    k = int((-1 + (1 + 8 * n) ** 0.5) / 2)
    # print(k)
    for i in range(1, k):
        summands.append(i)
        n -= i
    summands.append(n)
    return summands
# write test_optimal_summands function from optimal_summands(n) doc
def test_optimal_summands():
    """
    Some test cases for optimal_summands(n).
    """
    assert optimal_summands(1) == [1]
    assert optimal_summands(2) == [2]
    assert optimal_summands(5) == [1, 4]
    assert optimal_summands(8) == [1, 2, 5]
    print("Test cases passed...")

if __name__ == '__main__':
    if DEBUG:
        test_optimal_summands()
    input = sys.stdin.read()
    n = int(input)
    summands = optimal_summands(n)
    print(len(summands))
    for x in summands:
        print(x, end=' ')
