#Uses python3

import sys
DEBUG = False

def lcs3(a, b, c):
    """
    Find the length of the longest common subsequence among three sequences.

    Args:
        a (list): The first sequence.
        b (list): The second sequence.
        c (list): The third sequence.

    Returns:
        int: The length of the longest common subsequence.
    Examples:
        >>> lcs3([1, 2, 3], [2, 1, 3], [1, 3, 5])
        2
        >>> lcs3([8, 3, 2, 1, 7], [8, 2, 1, 3, 8, 10, 7], [6, 8, 3, 1, 4, 7])
        3
    """
    # use dynamic programming to find the longest common subsequence for three sequences
    # initialize the dp table
    dp_table = [[[0] * (len(c) + 1) for _ in range(len(b) + 1)] for _ in range(len(a) + 1)]
    # iterate through the dp table
    for i in range(len(a) + 1):
        for j in range(len(b) + 1):
            for k in range(len(c) + 1):
                # if i == 0 or j == 0 or k == 0:
                if i == 0 or j == 0 or k == 0:
                    # when either sequence is empty, the longest common subsequence is 0
                    dp_table[i][j][k] = 0
                elif a[i - 1] == b[j - 1] == c[k - 1]:
                    # when the last characters match, we add 1 to the previous longest common subsequence
                    # dp_table[i][j][k] = dp_table[i - 1][j - 1][k - 1] + 1
                    dp_table[i][j][k] = dp_table[i - 1][j - 1][k - 1] + 1
                # else:
                else:
                    # when the last characters don't match, we need to consider all possibilities
                    # dp_table[i - 1][j][k] = longest common subsequence of a[:i - 1] and b[:j] and c[:k]
                    # dp_table[i][j - 1][k] = longest common subsequence of a[:i] and b[:j - 1] and c[:k]
                    # dp_table[i][j][k - 1] = longest common subsequence of a[:i] and b[:j] and c[:k - 1]
                    dp_table[i][j][k] = max(dp_table[i - 1][j][k], dp_table[i][j - 1][k], dp_table[i][j][k - 1])
    # return the bottom right corner of the dp table
    return dp_table[len(a)][len(b)][len(c)]
    
# Write test cases
def test_cases():
    assert lcs3([1, 2, 3], [2, 1, 3], [1, 3, 5]) == 2
    assert lcs3([8, 3, 2, 1, 7], [8, 2, 1, 3, 8, 10, 7], [6, 8, 3, 1, 4, 7]) == 3
    print('All test cases passed.')

if __name__ == '__main__':
    if DEBUG:
        test_cases()
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    an = data[0]
    data = data[1:]
    a = data[:an]
    data = data[an:]
    bn = data[0]
    data = data[1:]
    b = data[:bn]
    data = data[bn:]
    cn = data[0]
    data = data[1:]
    c = data[:cn]
    print(lcs3(a, b, c))
