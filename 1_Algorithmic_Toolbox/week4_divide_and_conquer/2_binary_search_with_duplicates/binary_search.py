DEBUG = False

def binary_search(keys, query):
    """
    Find the first occurrence of an integer in the given sorted sequence of integers 
    (possibly with duplicates).

    Args:
        keys (list): Sorted sequence of integers.
        query (int): Integer to search for.

    Returns:
        int: The index of the first occurrence of the query in the keys list, or -1 if not found.

    Example:
        >>> binary_search([1, 2, 3, 4, 5], 1)
        0
        >>> binary_search([1, 2, 3, 4, 5], 5)
        4
        >>> binary_search([1, 2, 2, 2, 3, 3, 4, 4, 5], 3)
        4
        >>> binary_search([1, 2, 2, 2, 3, 3, 4, 4, 5], 4)
        6
    """
    left, right = 0, len(keys) - 1
    while left <= right:
        mid = (left + right) // 2
        if keys[mid] == query:
            # if the query is the first element in the list 
            # or the element before it is less than the query
            if mid == 0 or keys[mid-1] < query:
                # then return the index of the query
                return mid
            else:
                # otherwise, the query is not the first occurrence
                # so search the left half of the list
                right = mid - 1
        elif keys[mid] < query:
            left = mid + 1
        else:
            right = mid - 1
    return -1

# write test cases for binary_search(keys, query)
def test_binary_search():
    """
    Some test cases for binary_search(keys, query).
    """
    assert binary_search([1, 2, 3, 4, 5], 1) == 0
    assert binary_search([1, 2, 3, 4, 5], 5) == 4
    assert binary_search([1, 2, 2, 2, 3, 3, 4, 4, 5], 3) == 4
    assert binary_search([1, 2, 2, 2, 3, 3, 4, 4, 5], 4) == 6
    print("Test cases passed...")



if __name__ == '__main__':
    if DEBUG:
        test_binary_search()
    else:
        num_keys = int(input())
        input_keys = list(map(int, input().split()))
        assert len(input_keys) == num_keys

        num_queries = int(input())
        input_queries = list(map(int, input().split()))
        assert len(input_queries) == num_queries

        for q in input_queries:
            print(binary_search(input_keys, q), end=' ')
