# Uses python3
DEBUG = False

def edit_distance(s, t):
    """
    Calculate the edit distance between two strings.
    edit operations: insert, delete, and substitute. Each operation has unit cost.

    Args:
        s (str): The first string.
        t (str): The second string.

    Returns:
        int: The minimum number of operations required to transform string s into string t.
    Examples:
        >>> edit_distance('ab', 'ab')
        0
        >>> edit_distance('short', 'ports')
        3
        >>> edit_distance('editing', 'distance')
        5
    """
    # initialize the dp table
    dp_table = [[0] * (len(t) + 1) for _ in range(len(s) + 1)]
    # iterate through the dp table
    for i in range(len(s) + 1):
        for j in range(len(t) + 1):
            # if i == 0:
            if i == 0:
                # dp_table[i][j] = j
                dp_table[i][j] = j
            # elif j == 0:
            elif j == 0:
                # dp_table[i][j] = i
                dp_table[i][j] = i
            # elif s[i - 1] == t[j - 1]:
            elif s[i - 1] == t[j - 1]:
                # when the last characters match, we don't need to do anything
                # dp_table[i][j] = dp_table[i - 1][j - 1]
                dp_table[i][j] = dp_table[i - 1][j - 1]
            # else:
            else:
                # when the last characters don't match, we need to consider all possibilities
                # insert, delete, and substitute and choose the minimum
                # insert: dp_table[i][j - 1] + 1
                # delete: dp_table[i - 1][j] + 1
                # substitute: dp_table[i - 1][j - 1] + 1
                dp_table[i][j] = 1 + min(dp_table[i][j - 1], dp_table[i - 1][j], dp_table[i - 1][j - 1])
    # return the bottom right corner of the dp table base on s and t lengths
    return dp_table[len(s)][len(t)]
# Write test cases
def test_cases():
    assert edit_distance('ab', 'ab') == 0
    assert edit_distance('short', 'ports') == 3
    assert edit_distance('editing', 'distance') == 5
    print('All test cases passed.')

if __name__ == "__main__":
    if DEBUG:
        test_cases()
    print(edit_distance(input(), input()))
