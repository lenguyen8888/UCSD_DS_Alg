#Uses python3

import sys
DEBUG = False

def max_dot_product(a, b):
    """
    Calculates the maximum dot product of two lists.

    Args:
        a (list): The first list of integers.
        b (list): The second list of integers.

    Returns:
        int: The maximum dot product of the two lists.

    Example:
        >>> max_dot_product([23], [39])
        897
        >>> max_dot_product([1, 3, -5], [-2, 4, 1])
        23
        >>> max_dot_product([1, 2, 3], [4, 5, 6])
        32
    """
    
    res = 0
    # Using Greedy Algorithm, we want to maximize the sum of products of corresponding entries
    # So we sort the entries in both lists in descending order and multiply the largest entries together
    a.sort(reverse=True)
    b.sort(reverse=True)
    for i in range(len(a)):
        res += a[i] * b[i]
    return res
# write test_cases for max_dot_product(a, b)
def test_max_dot_product():
    """
    Some test cases for max_dot_product(a, b).
    """
    assert max_dot_product([23], [39]) == 897
    assert max_dot_product([1, 3, -5], [-2, 4, 1]) == 23
    assert max_dot_product([1, 2, 3], [4, 5, 6]) == 32
    print("Test cases passed...")

if __name__ == '__main__':
    if DEBUG:
        test_max_dot_product()
    else:
        input = sys.stdin.read()
        data = list(map(int, input.split()))
        n = data[0]
        a = data[1:(n + 1)]
        b = data[(n + 1):]
        print(max_dot_product(a, b))
    
