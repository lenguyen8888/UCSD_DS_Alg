#Uses python3

import sys
DEBUG = False

def lcs2(a, b):
    """
    Find the length of the longest common subsequence for two sequences.

    Args:
        a (list): The first sequence.
        b (list): The second sequence.

    Returns:
        int: The length of the longest common subsequence.
    Examples:
        >>> lcs2([2, 7, 5], [2, 5])
        2
        >>> lcs2([7], [1, 2, 3, 4])
        0
        >>> lcs2([2, 7, 8, 3], [5, 2, 8, 7])
        2
    """
    # use dynamic programming to find the longest common subsequence for two sequences
    # initialize the dp table
    dp_table = [[0] * (len(b) + 1) for _ in range(len(a) + 1)]
    # iterate through the dp table
    for i in range(len(a) + 1):
        for j in range(len(b) + 1):
            # if i == 0 or j == 0:
            if i == 0 or j == 0:
                # when either sequence is empty, the longest common subsequence is 0
                dp_table[i][j] = 0
            elif a[i - 1] == b[j - 1]:
                # when the last characters match, we add 1 to the previous longest common subsequence
                # dp_table[i][j] = dp_table[i - 1][j - 1] + 1
                dp_table[i][j] = dp_table[i - 1][j - 1] + 1
            # else:
            else:
                # when the last characters don't match, we need to consider all possibilities
                # dp_table[i - 1][j] = longest common subsequence of a[:i - 1] and b[:j]
                # dp_table[i][j - 1] = longest common subsequence of a[:i] and b[:j - 1]
                dp_table[i][j] = max(dp_table[i - 1][j], dp_table[i][j - 1])
    # return the bottom right corner of the dp table
    return dp_table[len(a)][len(b)]

# Write test cases
def test_cases():
    assert lcs2([2, 7, 5], [2, 5]) == 2
    assert lcs2([7], [1, 2, 3, 4]) == 0
    assert lcs2([2, 7, 8, 3], [5, 2, 8, 7]) == 2
    print('All test cases passed.')


if __name__ == '__main__':
    if DEBUG:
        test_cases()

    input = sys.stdin.read()
    data = list(map(int, input.split()))

    n = data[0]
    data = data[1:]
    a = data[:n]

    data = data[n:]
    m = data[0]
    data = data[1:]
    b = data[:m]

    print(lcs2(a, b))
