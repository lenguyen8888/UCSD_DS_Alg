import sys
DEBUG = False

def get_number_of_inversions(a, b, left, right):
    """
    Counts the number of inversions in a given array using the merge sort algorithm.

    Parameters:
    a (list): The input array.
    b (list): An auxiliary array used for merging.
    left (int): The starting index of the subarray.
    right (int): The ending index of the subarray.

    Returns:
    int: The number of inversions in the array.

    Examples:
    >>> get_number_of_inversions([2, 3, 9, 2, 9], [0, 0, 0, 0, 0], 0, 5)
    2
    >>> get_number_of_inversions([1, 2, 3, 4], [0, 0, 0, 0], 0, 4)
    0
    """
    number_of_inversions = 0
    if right - left <= 1:
        return number_of_inversions
    ave = (left + right) // 2
    number_of_inversions += get_number_of_inversions(a, b, left, ave)
    number_of_inversions += get_number_of_inversions(a, b, ave, right)
    # merge a[left:ave] and a[ave:right] to b[left:right] and count inversions
    i = left
    j = ave
    k = left
    while i < ave and j < right:
        if a[i] <= a[j]:
            b[k] = a[i]
            i += 1
        else:
            # a[i] > a[j]
            b[k] = a[j]
            j += 1
            number_of_inversions += ave - i
        k += 1
    while i < ave:
        b[k] = a[i]
        i += 1
        k += 1
    while j < right:
        b[k] = a[j]
        j += 1
        k += 1
    # copy b[left:right] to a[left:right]
    for i in range(left, right):
        a[i] = b[i]
    return number_of_inversions
# write test cases for get_number_of_inversions(a, b, left, right)
def test_cases():
    assert get_number_of_inversions([2, 3, 9, 2, 9], [0, 0, 0, 0, 0], 0, 5) == 2
    assert get_number_of_inversions([1, 2, 3, 4], [0, 0, 0, 0], 0, 4) == 0
    assert get_number_of_inversions([1, 2, 3, 1], [0, 0, 0, 0], 0, 4) == 1
    print("Test cases passed...")


if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        input = sys.stdin.read()
        n, *a = list(map(int, input.split()))
        b = n * [0]
        print(get_number_of_inversions(a, b, 0, len(a)))
