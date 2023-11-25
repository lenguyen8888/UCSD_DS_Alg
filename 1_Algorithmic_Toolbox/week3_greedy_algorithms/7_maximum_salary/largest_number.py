#Uses python3

import sys
DEBUG = False
import functools

def largest_number(a):
    """
    Returns the largest possible number that can be formed by concatenating the elements of the given list.

    Parameters:
    a (list): A list of integers.

    Returns:
    str: The largest possible number formed by concatenating the elements of the list.

    Example:
    >>> largest_number([21, 2])
    221
    >>> largest_number([9, 4, 6, 1, 9])
    99641
    >>> largest_number([23, 39, 92])
    923923
    """
    # The safe move is to consider digits from MSB => we can use string sorting
    # The optimal substructure is to consider the largest digit first
    # map each number to a string and sort the list of strings
    a = list(map(str, a))
    # sort the list of strings with a custom comparator
    # if ab > ba, then a > b
    # if ab < ba, then a < b
    # if ab == ba, then a = b
    # where a and b are strings
    
    # concatenate the strings in the sorted list
    def str_cmp(a, b):
        """
        Custom str_spec_cmp function to compare two strings a and b.
        """
        if a + b > b + a:
            return -1
        elif a + b < b + a:
            return 1
        else:
            return 0
    sorted_a = sorted(a, key=functools.cmp_to_key(str_cmp))
    return int(''.join(sorted_a))

# write test_largest_number function from largest_number(a) doc
def test_largest_number():
    """
    Some test cases for largest_number(a).
    """
    assert largest_number([21, 2]) == 221
    assert largest_number([9, 4, 6, 1, 9]) == 99641
    assert largest_number([23, 39, 92]) == 923923
    print("Test cases passed...")

if __name__ == '__main__':
    if DEBUG:
        test_largest_number()
    else:
        input = sys.stdin.read()
        data = input.split()
        a = data[1:]
        print(largest_number(a))
    
