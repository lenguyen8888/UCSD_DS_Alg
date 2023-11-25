# Uses python3
import sys
DEBUG = False

def get_fibonacci_last_digit_naive(n):
    if n <= 1:
        return n

    previous = 0
    current  = 1

    for _ in range(n - 1):
        previous, current = current, previous + current

    return current % 10
# Reimplement get_fib_last_digit_fast by calculating only the last digit in each iteration
def get_fib_last_digit_fast(n):
    """
    Calculates the last digit of the nth Fibonacci number.

    Parameters:
    n (int): The index of the Fibonacci number to calculate.

    Returns:
    int: The last digit of the nth Fibonacci number.

    Example:
        >>> get_fib_last_digit_fast(3)
        2
        >>> get_fib_last_digit_fast(10)
        5
        >>> get_fib_last_digit_fast(331)
        9
        >>> get_fib_last_digit_fast(327305)
        5
    """
    # Iterate from 2 to n and keep track of just the 2 most recent Fibonacci numbers
    # and their last digits, using % 10 every time to calculate the last digit
    # Don't use a list to store all the Fibonacci numbers
    if n <= 1:
        return n
    else:
        fib_prev = 0
        fib_curr = 1
        for i in range(2, n + 1):
            fib_prev, fib_curr = fib_curr, (fib_prev + fib_curr) % 10
        return fib_curr

# Write test cases
def test_cases():
    assert get_fib_last_digit_fast(3) == 2
    assert get_fib_last_digit_fast(10) == 5
    assert get_fib_last_digit_fast(331) == 9
    assert get_fib_last_digit_fast(327305) == 5
    print('All test cases passed.')


if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        input = sys.stdin.read()
        n = int(input)
        print(get_fib_last_digit_fast(n))
