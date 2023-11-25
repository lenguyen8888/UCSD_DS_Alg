# Uses python3
import sys
DEBUG = False

def fibonacci_partial_sum_naive(from_, to):
    _sum = 0

    current = 0
    _next  = 1

    for i in range(to + 1):
        if i >= from_:
            _sum += current

        current, _next = _next, current + _next

    return _sum % 10

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

def fibonacci_partial_sum_fast(from_, to):
    """
    Calculates the last digit of the sum of the Fibonacci numbers from the
    from_th Fibonacci number to the to_th Fibonacci number.

    Parameters:
    from_ (int): The index of the first Fibonacci number to sum.
    to (int): The index of the last Fibonacci number to sum.

    Returns:
    int: The last digit of the sum of the Fibonacci numbers from the
        from_th Fibonacci number to the to_th Fibonacci number.

    Example:
        >>> fibonacci_partial_sum_fast(3, 7)
        1
        >>> fibonacci_partial_sum_fast(10, 10)
        5
        >>> fibonacci_partial_sum_fast(10, 200)
        2
    """
    # Use the formula: F(m) + F(m + 1) + ... + F(n) = F(n + 2) - F(m + 1)
    # where F(n) is the nth Fibonacci number
    # and F(m) is the mth Fibonacci number
    # and F(n + 2) is the (n + 2)th Fibonacci number
    # and F(m + 1) is the (m + 1)th Fibonacci number
    # and F(n + 2) - F(m + 1) is the sum of the Fibonacci numbers from the
    # from_th Fibonacci number to the to_th Fibonacci number
    # and F(n + 2) - F(m + 1) is the sum of the Fibonacci numbers from the
    # from_th Fibonacci number to the to_th Fibonacci number modulo 10
    # and F(n + 2) - F(m + 1) is the last digit of the sum of the Fibonacci
    # numbers from the from_th Fibonacci number to the to_th Fibonacci number
    if from_ == to:
        return fast_fib_last_digit(from_)
    else:
        return (fast_fib_last_digit(to + 2) - fast_fib_last_digit(from_ + 1)) % 10

# Write test code for fast_fib_last_digit(n)
def test_fast_fib_last_digit():
    """
    Tests fast_fib_last_digit(n) using the examples from the docstring.
    """
    assert fast_fib_last_digit(3) == 2
    assert fast_fib_last_digit(331) == 9
    assert fast_fib_last_digit(327305) == 5
    print('Test passed')

if __name__ == '__main__':
    if DEBUG:
        test_fast_fib_last_digit()
    else:
        input = sys.stdin.read();
        from_, to = map(int, input.split())
        print(fibonacci_partial_sum_fast(from_, to))
