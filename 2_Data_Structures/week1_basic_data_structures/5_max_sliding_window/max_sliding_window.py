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
        # This loop is O(m) because
        # each element is pushed and popped at most once.

        # remove all elements smaller than the current element
        while stack and sequence[i] >= sequence[stack[-1]]:
            stack.pop()
        # add the current element to the stack
        stack.append(i)
    # add the maximum of the first window to the maximums list
    # for the 1st window, the maximum is the first element in the stack
    maximums.append(sequence[stack[0]])

    for i in range(m, n):
        # Each iteration is O(1) because 
        # each element is pushed and popped at most once.

        # remove all elements smaller than the current element
        # from the tail of the stack
        while stack and sequence[i] >= sequence[stack[-1]]:
            stack.pop()
        # remove the elements that are outside the current window
        if stack and stack[0] <= i - m:
            stack.pop(0)
        # add the current element to the stack
        stack.append(i)
        # add the maximum of the current window to the maximums list
        maximums.append(sequence[stack[0]])

    # Overall, the time complexity is O(n) because
    # each element is pushed and popped at most once.
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
