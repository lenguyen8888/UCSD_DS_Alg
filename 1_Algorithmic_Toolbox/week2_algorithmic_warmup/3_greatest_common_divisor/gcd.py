# Uses python3
import sys

DEBUG = False

def gcd_naive(a, b):
    current_gcd = 1
    for d in range(2, min(a, b) + 1):
        if a % d == 0 and b % d == 0:
            if d > current_gcd:
                current_gcd = d

    return current_gcd

# Reimplement gcd_fast using Euclid's algorithm
def gcd_fast(a, b):
    """
    Calculates the greatest common divisor of two numbers.

    Parameters:
    a (int): The first number.
    b (int): The second number.

    Returns:
    int: The greatest common divisor of the two numbers.

    Examples:
    >>> gcd_fast(18, 35)
    1
    >>> gcd_fast(28851538, 1183019)
    17657
    """
    # Euclid's algorithm
    # Divide the larger number by the smaller number
    # If the remainder is 0, then the smaller number is the GCD
    # If the remainder is not 0, then the smaller number is the new larger number
    # and the remainder is the new smaller number
    if a < b:
        a, b = b, a
    while b != 0:
        a, b = b, a % b
    return a

# Write test cases
def test_cases():
    assert gcd_fast(18, 35) == 1
    assert gcd_fast(28851538, 1183019) == 17657
    print('All test cases passed.')

if __name__ == "__main__":
    if DEBUG:
        test_cases()
    else:
        input = sys.stdin.read()
        a, b = map(int, input.split())
        print(gcd_fast(a, b))
