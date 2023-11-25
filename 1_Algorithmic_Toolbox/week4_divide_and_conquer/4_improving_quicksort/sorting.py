# Uses python3
import sys
import random

DEBUG = False

def partition3(a, l, r):
    """
    Partitions the array 'a' into three parts based on a pivot element.
    
    Parameters:
    a (list): The array to be partitioned.
    l (int): The left index of the array.
    r (int): The right index of the array.
    
    Returns:
    tuple: A tuple containing the indices of the two partitions.
    """
    x = a[l]
    lt = l
    i = l
    gt = r

    while i <= gt:
        if a[i] < x:
            a[i], a[lt] = a[lt], a[i]
            lt += 1
            i += 1
        elif a[i] > x:
            a[i], a[gt] = a[gt], a[i]
            gt -= 1
        else:
            i += 1

    return lt, gt


def partition2(a, l, r):
    """
    Partitions the array 'a' based on a pivot element and returns the index of
    the pivot element after partitioning.

    Parameters:
    a (list): The array to be partitioned.
    l (int): The leftmost index of the subarray to be partitioned.
    r (int): The rightmost index of the subarray to be partitioned.

    Returns:
    int: The index of the pivot element after partitioning.

    """
    x = a[l]
    j = l
    for i in range(l + 1, r + 1):
        if a[i] <= x:
            j += 1
            a[i], a[j] = a[j], a[i]
    a[l], a[j] = a[j], a[l]
    return j


def randomized_quick_sort(a, l, r):
    if l >= r:
        return
    k = random.randint(l, r)
    a[l], a[k] = a[k], a[l]
    #use partition3
    m = partition2(a, l, r)
    randomized_quick_sort(a, l, m - 1);
    randomized_quick_sort(a, m + 1, r);

def randomized_quick_sort_3way(a, l, r):
    if l >= r:
        return
    k = random.randint(l, r)
    a[l], a[k] = a[k], a[l]
    # use partition3
    lt, gt = partition3(a, l, r)
    randomized_quick_sort_3way(a, l, lt - 1)
    randomized_quick_sort_3way(a, gt + 1, r)

# write test_cases for randomized_quick_sort(a, l, r)
# with duplicates in the array
def test_cases():
    assert randomized_quick_sort([2, 3, 9, 2, 2], 0, 4) == [2, 2, 2, 3, 9]
    assert randomized_quick_sort([1, 2, 3, 4], 0, 3) == [1, 2, 3, 4]
    assert randomized_quick_sort([1, 2, 3, 1], 0, 3) == [1, 1, 2, 3]
    print("Test cases passed...")


if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        input = sys.stdin.read()
        n, *a = list(map(int, input.split()))
        randomized_quick_sort_3way(a, 0, n - 1)
        for x in a:
            print(x, end=' ')
