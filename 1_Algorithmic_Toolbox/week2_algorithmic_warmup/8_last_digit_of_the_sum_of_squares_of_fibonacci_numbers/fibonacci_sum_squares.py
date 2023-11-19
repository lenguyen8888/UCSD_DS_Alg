# Uses python3
from sys import stdin

def fibonacci_sum_squares_naive(n):
    if n <= 1:
        return n

    previous = 0
    current  = 1
    sum      = 1

    for _ in range(n - 1):
        previous, current = current, previous + current
        sum += current * current

    return sum % 10

# Implement fast_fib_last_digit by using Pisano period == 60
def fast_fib_last_digit(n):
    """
    Calculates the last digit of the nth Fibonacci number.

    Parameters:
    n (int): The index of the Fibonacci number to calculate.

    Returns:
    int: The last digit of the nth Fibonacci number.

    Reference:
    - Wikipedia: https://en.wikipedia.org/wiki/Fibonacci_number

    Example:
        >>> fast_fib_last_digit(3)
        2
        >>> fast_fib_last_digit(331)
        9
        >>> fast_fib_last_digit(327305)
        5
    """
    # Use the formula: F(n) % 10 = F(n % 60) % 10
    # where F(n) is the nth Fibonacci number
    # and F(n % 60) is the nth Fibonacci number modulo 60
    PISANO_PERIOD_10 = 60
    n = n % PISANO_PERIOD_10
    if n <= 1:
        return n
    else:
        fib_prev = 0
        fib_curr = 1
        for i in range(2, n + 1):
            fib_prev, fib_curr = fib_curr, (fib_prev + fib_curr) % 10
        return fib_curr

# Implement fibonacci_sum_squares_fast(n)
def fibonacci_sum_squares_fast(n):
    """
    Calculates the last digit of the sum of the squares of the first n
    Fibonacci numbers.

    Parameters:
    n (int): The number of Fibonacci numbers to sum.

    Returns:
    int: The last digit of the sum of the squares of the first n Fibonacci
        numbers.

    Example:
        >>> fibonacci_sum_squares_fast(7)
        3
        >>> fibonacci_sum_squares_fast(73)
        1
    """
    # Use the formula: F(0)^2 + F(1)^2 + ... + F(n)^2 = F(n) * F(n + 1)
    # where F(n) is the nth Fibonacci number
    fib_n = fast_fib_last_digit(n)
    fib_n_plus_1 = fast_fib_last_digit(n + 1)
    return (fib_n * fib_n_plus_1) % 10

if __name__ == '__main__':
    n = int(stdin.read())
    print(fibonacci_sum_squares_fast(n))
