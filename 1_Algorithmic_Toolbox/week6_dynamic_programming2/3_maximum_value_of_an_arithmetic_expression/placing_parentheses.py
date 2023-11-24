# Uses python3
DEBUG = False

def evalt(a, b, op):
    if op == '+':
        return a + b
    elif op == '-':
        return a - b
    elif op == '*':
        return a * b
    else:
        assert False
# write a function that finds the minimum and maximum values of an arithmetic expression
def min_and_max(i, j, min_table, max_table, operators):
    """
    Finds the minimum and maximum values of an arithmetic expression.

    Args:
        i (int): The starting index of the arithmetic expression.
        j (int): The ending index of the arithmetic expression.
        min_table (list): The dp table for the minimum values.
        max_table (list): The dp table for the maximum values.
        operators (list): The list of operators in the arithmetic expression.

    Returns:
        tuple: The minimum and maximum values of the arithmetic expression.

    Examples:
        >>> min_and_max(1, 3,
         [[0, 0, 0, 0], [0, 0, 0, 0]
        , [0, 0, 3, -4], [0, 0, 0, 0]]

        , [[0, 0, 0, 0], [0, 0, 0, 0]
        , [0, 0, -4, 3], [0, 0, 0, 0]]

        , ['+', '-', '*']
        )
        (-4, 3)
    """
    # initialize the minimum and maximum values
    min_value = float('inf')
    max_value = float('-inf')
    # iterate through the range of i to j
    for k in range(i, j):
        # a = evalt(max_table[i][k], max_table[k + 1][j], operators[k])
        a = evalt(max_table[i][k], max_table[k + 1][j], operators[k])
        # b = evalt(max_table[i][k], min_table[k + 1][j], operators[k])
        b = evalt(max_table[i][k], min_table[k + 1][j], operators[k])
        # c = evalt(min_table[i][k], max_table[k + 1][j], operators[k])
        c = evalt(min_table[i][k], max_table[k + 1][j], operators[k])
        # d = evalt(min_table[i][k], min_table[k + 1][j], operators[k])
        d = evalt(min_table[i][k], min_table[k + 1][j], operators[k])
        # min_value = min(min_value, a, b, c, d)
        min_value = min(min_value, a, b, c, d)
        # max_value = max(max_value
        max_value = max(max_value, a, b, c, d)
    # return the minimum and maximum values
    return min_value, max_value

def get_maximum_value(dataset):
    """
    Calculates the maximum value of an arithmetic expression.

    Args:
        dataset (str): The arithmetic expression in the form of a string.

    Returns:
        int: The maximum value of the arithmetic expression.

    Examples:
        >>> get_maximum_value('1+5')
        6
        >>> get_maximum_value('5-8+7*4-8+9')
        200
    """
    # using dynamic programming to find the maximum value of an arithmetic expression
    # of the form: a1 op1 a2 op2 a3 op3 ... an-1 opn an
    # split the string into a list of integers and operators
    integers = [int(x) for x in dataset if x.isdigit()]
    operators = [x for x in dataset if not x.isdigit()]
    # initialize the dp tables
    min_table = [[0] * len(integers) for _ in range(len(integers))]
    max_table = [[0] * len(integers) for _ in range(len(integers))]
    # set all the diagonal values to the integers
    for i in range(len(integers)):
        min_table[i][i] = integers[i]
        max_table[i][i] = integers[i]
    # iterate through the dp tables
    for s in range(1, len(integers)):
        for i in range(len(integers) - s):
            j = i + s
            min_table[i][j], max_table[i][j] = min_and_max(i, j, min_table, max_table, operators)
    # return the top right corner of the dp table
    return max_table[0][len(integers) - 1]


# write test cases
def test_cases():
    assert get_maximum_value('1+5') == 6
    assert get_maximum_value('5-8+7*4-8+9') == 200
    print('All test cases passed.')


if __name__ == "__main__":
    if DEBUG:
        test_cases()
    print(get_maximum_value(input()))
