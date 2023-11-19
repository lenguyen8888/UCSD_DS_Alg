# Uses python3
import sys

def lcm_naive(a, b):
    for l in range(1, a*b + 1):
        if l % a == 0 and l % b == 0:
            return l

    return a*b

# Implement gcd_fast using Euclid's algorithm
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

# Implement lcm_fast using the formula: lcm(a, b) = (a * b) / gcd(a, b)
def lcm_fast(a, b):
    """
    Calculates the least common multiple of two numbers.

    Parameters:
    a (int): The first number.
    b (int): The second number.

    Returns:
    int: The least common multiple of the two numbers.

    Examples:
    >>> lcm_fast(6, 8)
    24
    >>> lcm_fast(18, 35)
    630
    """
    return (a * b) // gcd_fast(a, b)

if __name__ == '__main__':
    input = sys.stdin.read()
    a, b = map(int, input.split())
    print(lcm_fast(a, b))

