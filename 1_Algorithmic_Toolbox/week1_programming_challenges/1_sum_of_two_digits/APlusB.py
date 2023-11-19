# python3


def sum_of_two_digits(first_digit, second_digit):
    """
    Calculates the sum of two digits.

    Parameters:
    first_digit (int): The first digit.
    second_digit (int): The second digit.

    Returns:
    int: The sum of the two digits.

    Examples:
    >>> sum_of_two_digits(4, 5)
    9
    >>> sum_of_two_digits(0, 0)
    
    """
    return first_digit + second_digit

if __name__ == '__main__':
    a, b = map(int, input().split())
    print(sum_of_two_digits(a, b))
