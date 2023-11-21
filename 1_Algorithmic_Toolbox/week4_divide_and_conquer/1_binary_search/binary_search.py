DEBUG = False
def binary_search(keys, query):
    """
    Perform binary search to find the index of the query in the given list of keys.

    Args:
        keys (list): The list of keys to search in.
        query: The element to search for.

    Returns:
        int: The index of the query in the keys list if found, -1 otherwise.
    Example:
        >>> binary_search([1, 2, 3, 4, 5], 1)
        0
        >>> binary_search([1, 2, 3, 4, 5], 5)
        4
        >>> binary_search([1, 2, 3, 4, 5], 6)
        -1
    """
    left, right = 0, len(keys) - 1
    while left <= right:
        mid = (left + right) // 2
        if keys[mid] == query:
            return mid
        elif keys[mid] < query:
            left = mid + 1
        else:
            right = mid - 1
    return -1

# write test cases for binary_search(keys, query)
def test_binary_search():
    """
    Some test cases for
    binary_search(keys, query).
    """
    assert binary_search([1, 2, 3, 4, 5], 1) == 0
    assert binary_search([1, 2, 3, 4, 5], 5) == 4
    assert binary_search([1, 2, 3, 4, 5], 6) == -1
    print("Test cases passed...")



if __name__ == '__main__':
    if DEBUG:
        test_binary_search()
    num_keys = int(input())
    input_keys = list(map(int, input().split()))
    assert len(input_keys) == num_keys

    num_queries = int(input())
    input_queries = list(map(int, input().split()))
    assert len(input_queries) == num_queries

    for q in input_queries:
        print(binary_search(input_keys, q), end=' ')
