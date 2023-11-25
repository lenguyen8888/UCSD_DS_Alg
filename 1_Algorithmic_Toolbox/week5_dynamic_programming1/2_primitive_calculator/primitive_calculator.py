# Uses python3
import sys
DEBUG = False

def optimal_sequence(n):
    """
    Returns the optimal sequence of numbers to reach the given number 'n' using the following operations:
    - Subtract 1
    - Divide by 2 (if divisible by 2)
    - Divide by 3 (if divisible by 3)
    
    Parameters:
    n (int): The target number
    
    Returns:
    list: The optimal sequence of numbers

    Examples:
    >>> optimal_sequence(1)
    [1]
    >>> optimal_sequence(5)
    [1, 2, 4, 5]
    >>> optimal_sequence(96234)
    [1, 3, 9, 10, 11, 33, 99, 297, 891, 2673, 8019, 16038, 16039, 32078, 96234]
    
    """
    sequence = []
    dp_solution = solution(n)
    while n >= 1:
        sequence.append(n)
        # trace back to the previous number
        # in dp_solution
        if dp_solution[n - 1] == dp_solution[n] - 1:
            n = n - 1
        elif n % 2 == 0 and dp_solution[n // 2] == dp_solution[n] - 1:
            n = n // 2
        elif n % 3 == 0 and dp_solution[n // 3] == dp_solution[n] - 1:
            n = n // 3
    return list(reversed(sequence))
    
# write solution function using dynamic programming
def solution(n):
    # initialize min_num_operations
    min_num_operations = [0] + [float('inf')] * n
    # iterate from 1 to n
    for i in range(1, n + 1):
        # min_num_operations[i] = min_num_operations[i - 1] + 1
        min_num_operations[i] = min_num_operations[i - 1] + 1
        # if i % 2 == 0:
        if i % 2 == 0:
            # min_num_operations[i] = min(min_num_operations[i], min_num_operations[i // 2] + 1)
            min_num_operations[i] = min(min_num_operations[i], min_num_operations[i // 2] + 1)
        # if i % 3 == 0:
        if i % 3 == 0:
            # min_num_operations[i] = min(min_num_operations[i], min_num_operations[i // 3] + 1)
            min_num_operations[i] = min(min_num_operations[i], min_num_operations[i // 3] + 1)
    # return min_num_opertions
    return min_num_operations

# write test_cases
def test_cases():
    # calculation sequences are not unique
    assert optimal_sequence(1) == [1]
    assert len(optimal_sequence(5)) == len([1, 2, 4, 5])
    assert len(optimal_sequence(96234)) == len([1, 3, 9, 10, 11, 33, 99, 297, 891, 2673, 8019, 16038, 16039, 32078, 96234])
    print("Test cases passed...")

if __name__ == '__main__':
    if (DEBUG):
        test_cases()
    else:
        input = sys.stdin.read()
        n = int(input)
        sequence = list(optimal_sequence(n))
        print(len(sequence) - 1)
        for x in sequence:
            print(x, end=' ')
