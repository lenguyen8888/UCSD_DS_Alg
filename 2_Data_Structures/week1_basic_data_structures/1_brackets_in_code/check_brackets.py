# python3
DEBUG = False
from collections import namedtuple

Bracket = namedtuple("Bracket", ["char", "position"])


def are_matching(left, right):
    return (left + right) in ["()", "[]", "{}"]


def find_mismatch(text):
    """
    Finds the position of the first mismatched bracket in the given text.

    Args:
        text (str): The text to check for mismatched brackets.

    Returns:
        int or str: If the brackets are balanced, returns "Success". 
        Otherwise, returns the position of the first mismatched bracket.
    Examples:
        >>> find_mismatch('[]')
        'Success'
        >>> find_mismatch('{}[]')
        'Success'
        >>> find_mismatch('[()]')
        'Success'
        >>> find_mismatch('(())')
        'Success'
        >>> find_mismatch('{[]}()')
        'Success'
        >>> find_mismatch('{')
        1
        >>> find_mismatch('{[}')
        3
        >>> find_mismatch('foo(bar);')
        'Success'
        >>> find_mismatch('foo(bar[i);')
        10
    """
    opening_brackets_stack = []
    for i, next in enumerate(text):
        if next in "([{":
            # create a Bracket(next, i+1)
            # push it onto the stack
            opening_brackets_stack.append(Bracket(next, i + 1))

        if next in ")]}":
            # if the stack is empty
            if not opening_brackets_stack:
                # return i + 1
                return i + 1
            # pop the top element from the stack
            top = opening_brackets_stack.pop()
            # if the top element doesn't match next
            if not are_matching(top.char, next):
                # return i + 1
                return i + 1

    # if the stack is empty
    if not opening_brackets_stack:
        # return Success
        return "Success"
    # else
    else:
        # return the position of the top element
        return opening_brackets_stack.pop().position
    
# Write test cases
def test_cases():
    assert find_mismatch('[]') == 'Success'
    assert find_mismatch('{}[]') == 'Success'
    assert find_mismatch('[()]') == 'Success'
    assert find_mismatch('(())') == 'Success'
    assert find_mismatch('{[]}()') == 'Success'
    assert find_mismatch('{') == 1
    assert find_mismatch('{[}') == 3
    assert find_mismatch('foo(bar);') == 'Success'
    assert find_mismatch('foo(bar[i);') == 10
    print('All test cases passed.')



def main():
    if DEBUG:
        test_cases()
    else:
        text = input()
        mismatch = find_mismatch(text)
        # Printing answer, write your code here
        print(mismatch)


if __name__ == "__main__":
    main()
