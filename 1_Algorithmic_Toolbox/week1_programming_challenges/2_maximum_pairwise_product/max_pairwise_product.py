def max_pairwise_product(numbers):
    n = len(numbers)
    max_product = 0
    for first in range(n):
        for second in range(first + 1, n):
            max_product = max(max_product,
                numbers[first] * numbers[second])

    return max_product

# ReImplementing max_pairwise_product() using a faster algorithm
def max_pairwise_product_fast(numbers):
    """
    Calculates the maximum pairwise product of a list of numbers.

    Parameters:
    numbers (list): A list of numbers.

    Returns:
    int: The maximum pairwise product of the numbers.

    Examples:
    >>> max_pairwise_product_fast([1, 2, 3])
    6
    >>> max_pairwise_product_fast([7, 5, 14, 2, 8, 8, 10, 1, 2, 3])
    140
    """
    n = len(numbers)
    max_product = 0
    max1 = max(numbers)
    numbers.remove(max1)
    max2 = max(numbers)
    max_product = max1 * max2
    return max_product


if __name__ == '__main__':
    input_n = int(input())
    input_numbers = [int(x) for x in input().split()]
    print(max_pairwise_product(input_numbers))
