# Uses python3
import sys
DEBUG = False
def fibonacci_sum_naive(n):
    if n <= 1:
        return n

    previous = 0
    current  = 1
    _sum      = 1

    for _ in range(n - 1):
        previous, current = current, previous + current
        _sum += current

    return _sum % 10

# Implement get_pisano_period(m)
def get_pisano_period(m):
    """
    Calculates the Pisano period for a given integer m.

    Parameters:
    m (int): The integer to calculate the Pisano period for.

    Returns:
    int: The Pisano period for m.

    Examples:
    >>> get_pisano_period(2)
    3
    >>> get_pisano_period(3)
    8
    >>> get_pisano_period(4)
    6
    >>> get_pisano_period(5)
    20

    Source:
    More information on Pisano period can be found at:
    https://en.wikipedia.org/wiki/Pisano_period
    """
    current = 1
    previous = 0
    period = 0
    while True:
        previous, current = current, (previous + current) % m
        period += 1
        if previous == 0 and current == 1:
            return period

# Implement get_fib_mod_m(n, m)
def get_fib_mod_m(n, m):
    """
    Calculates the nth Fibonacci number modulo m.

    Parameters:
    n (int): The index of the Fibonacci number to calculate.
    m (int): The modulo.

    Returns:
    int: The nth Fibonacci number modulo m.

    Example:
        >>> get_fib_mod_m(2015, 3)
        1
        >>> get_fib_mod_m(239, 1000)
        161
        >>> get_fib_mod_m(2816213588, 239)
        151
    """
    # Use the formula: F(n) % m = F(n % pisano_period(m)) % m
    # where pisano_period(m) is the Pisano period of m
    # and F(n) is the nth Fibonacci number
    # and F(n % pisano_period(m)) is the nth Fibonacci number modulo m
    pisano_period = get_pisano_period(m)
    n = n % pisano_period
    if n <= 1:
        return n
    else:
        fib_prev = 0
        fib_curr = 1
        for i in range(2, n + 1):
            fib_prev, fib_curr = fib_curr, (fib_prev + fib_curr) % m
        return fib_curr

# Implement fibonacci_sum_fast(n)
def fibonacci_sum_fast(n):
    """
    Calculates the last digit of the sum of the first n Fibonacci numbers.

    Parameters:
    n (int): The number of Fibonacci numbers to sum.

    Returns:
    int: The last digit of the sum of the first n Fibonacci numbers.

    Example:
        >>> fibonacci_sum_fast(3)
        4
        >>> fibonacci_sum_fast(100)
        5
    """
    # Use the formula: F(0) + F(1) + ... + F(n) = F(n + 2) - 1
    # where F(n) is the nth Fibonacci number
    # and F(n + 2) is the (n + 2)th Fibonacci number
    # and F(n + 2) - 1 is the sum of the first n Fibonacci numbers
    return (get_fib_mod_m(n + 2, 10) - 1) % 10

# Write test code for fibonacci_sum_fast(n)
def test_fibonacci_sum_fast():
    """
    Tests fibonacci_sum_fast(n) using the examples from the docstring.
    """
    assert fibonacci_sum_fast(3) == 4
    assert fibonacci_sum_fast(100) == 5
    print('Test passed')

if __name__ == '__main__':
    if DEBUG:
        test_fibonacci_sum_fast()

    input = sys.stdin.read()
    n = int(input)
    print(fibonacci_sum_fast(n))
