# Uses python3
DEBUG = False

def calc_fib(n):
    """
    Calculates the nth Fibonacci number.

    Parameters:
    n (int): The index of the Fibonacci number to calculate.

    Returns:
    int: The nth Fibonacci number.

    Example:
        >>> calc_fib(3)
        2
        >>> calc_fib(10)
        55
        >>> calc_fib(0)
        0
        >>> calc_fib(1)
        1
    """
    if (n <= 1):
        return n

    return calc_fib(n - 1) + calc_fib(n - 2)

# Reinplementing calc_fib() using a faster algorithm
def calc_fib_fast(n):
    """
    Calculates the nth Fibonacci number.

    Parameters:
    n (int): The index of the Fibonacci number to calculate.

    Returns:
    int: The nth Fibonacci number.

    Example:
        >>> calc_fib_fast(3)
        2
        >>> calc_fib_fast(10)
        55
        >>> calc_fib_fast(0)
        0
        >>> calc_fib_fast(1)
        1
    """
    if n <= 1:
        return n
    else:
        fib = [0, 1]
        for i in range(2, n + 1):
            fib.append(fib[i - 1] + fib[i - 2])
        return fib[n]
# Write test cases
def test_cases():
    assert calc_fib_fast(3) == 2
    assert calc_fib_fast(10) == 55
    assert calc_fib_fast(0) == 0
    assert calc_fib_fast(1) == 1
    print('All test cases passed.')

if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        n = int(input())
        print(calc_fib_fast(n))
