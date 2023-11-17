# Uses python3
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

n = int(input())
print(calc_fib(n))
