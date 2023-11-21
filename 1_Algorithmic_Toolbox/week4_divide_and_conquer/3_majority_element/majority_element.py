# Uses python3
import sys

DEBUG = False

def get_majority_element(a, left, right):
    # sort the list
    a.sort()
    # linearly scan the list to find the majority element 
    # that occurs more than n/2 times
    count = 1
    for i in range(1, len(a)):
        if a[i] == a[i-1]:
            count += 1
        else:
            count = 1
        if count > len(a)/2:
            return a[i]
    return -1


# write test cases for get_majority_element(a, left, right)
def test_get_majority_element():
    """
    Some test cases for get_majority_element(a, left, right).
    """
    assert get_majority_element([2, 3, 9, 2, 2], 0, 5) == 2
    assert get_majority_element([1, 2, 3, 4], 0, 4) == -1
    assert get_majority_element([1, 2, 3, 1], 0, 4) == -1
    print("Test cases passed...")


if __name__ == '__main__':
    if DEBUG:
        test_get_majority_element()
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    if get_majority_element(a, 0, n) != -1:
        print(1)
    else:
        print(0)
