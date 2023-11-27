# python3
DEBUG = False

def heapify(data, n, i, swap_list):
    """
    Rearranges the elements in the data list to satisfy the heap property starting from the given index.

    Parameters:
    - data (list): The list of elements Zto be heapified.
    - n (int): The size of the heap.
    - i (int): The index of the element to start heapifying from.
    - swap_list (list): A list to store the swap operations performed during heapification.ZZ

    Returns:
    None
    """
    while True:
        smallest = i
        left = 2 * i + 1
        right = 2 * i + 2

        # Check if left child is smaller than current smallest
        if left < n and data[left] < data[smallest]:
            smallest = left

        # Check if right child is smaller than current smallest
        if right < n and data[right] < data[smallest]:
            smallest = right

        # If the smallest is not the current node, swap it with the smallest child
        if smallest != i:
            data[i], data[smallest] = data[smallest], data[i]
            swap_list.append((i, smallest))
            i = smallest  # Move down the tree
        else:
            break

def build_heap(data):
    """
    Builds a heap from the given data list.

    Parameters:
    data (list): The list of elements to build the heap from.

    Returns:
    list: The list of swap operations performed to build the heap.
    Examples:
        >>> build_heap([5, 4, 3, 2, 1])
        [(1, 4), (0, 1), (1, 3)]
        >>> build_heap([1, 2, 3, 4, 5])
        []
        >>> build_heap([1, 2, 3, 4, 5, 6, 7, 8, 9, 10])
        []
    """
    n = len(data)
    swap_list = []

    # Start from the last non-leaf node and heapify each node
    for i in range(n // 2 - 1, -1, -1):
        heapify(data, n, i, swap_list)

    return swap_list

# write test_cases
def test_cases():
    assert build_heap([5, 4, 3, 2, 1]) == [(1, 4), (0, 1), (1, 3)]
    assert build_heap([1, 2, 3, 4, 5]) == []
    assert build_heap([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]) == []
    print('All test cases passed.')


def main():
    n = int(input())
    data = list(map(int, input().split()))
    assert len(data) == n

    swaps = build_heap(data)

    print(len(swaps))
    for i, j in swaps:
        print(i, j)


if __name__ == "__main__":
    if DEBUG:
        test_cases()
    else:
        main()
