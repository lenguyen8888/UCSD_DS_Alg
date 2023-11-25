# python3

DEBUG = False

def max_sliding_window_optimized(sequence, m):
    """
    Returns a list of maximum values in a sliding window of size m over the given sequence.

    Args:
        sequence (list): The input sequence of numbers.
        m (int): The size of the sliding window.

    Returns:
        list: A list of maximum values in the sliding window.
    Examples:
        >>> max_sliding_window_optimized([2, 7, 3, 1, 5, 2, 6, 2], 4)
        [7, 7, 5, 6, 6]
        >>> max_sliding_window_optimized([2, 7, 3, 1, 5, 2, 6, 2], 2)
        [7, 7, 3, 5, 5, 6, 6]
    """
    n = len(sequence)
    maximums = []
    stack = []
    for i in range(m):
        while stack and sequence[i] >= sequence[stack[-1]]:
            stack.pop()
        stack.append(i)
    maximums.append(sequence[stack[0]])

    for i in range(m, n):
        while stack and sequence[i] >= sequence[stack[-1]]:
            stack.pop()
        if stack and stack[0] <= i - m:
            stack.pop(0)
        stack.append(i)
        maximums.append(sequence[stack[0]])

    return maximums

# write test_cases
def test_cases():
    assert max_sliding_window_optimized([2, 7, 3, 1, 5, 2, 6, 2], 4) == [7, 7, 5, 6, 6]
    assert max_sliding_window_optimized([2, 7, 3, 1, 5, 2, 6, 2], 2) == [7, 7, 3, 5, 5, 6, 6]
    print('All test cases passed.')

if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        n = int(input())
        sequence = [int(i) for i in input().split()]
        assert len(sequence) == n
        m = int(input())
        print(*max_sliding_window_optimized(sequence, m))
