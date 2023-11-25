# Uses python3
import sys
DEBUG = False

def get_optimal_value(capacity, weights, values):
    """
    Calculates the maximum value that can be obtained by filling a knapsack with fractional items.

    Args:
        capacity (float): The maximum weight capacity of the knapsack.
        weights (list): A list of weights of the items.
        values (list): A list of values of the items.

    Returns:
        float: The maximum value that can be obtained.

    Example:
        >>> get_optimal_value(50, [20, 50, 30], [60, 100, 120])
        180.0
        >>> get_optimal_value(10, [30], [500])
        166.6667
        >>> get_optimal_value(0, [30], [500])
        0.0

    """
    value = 0.
    # Sort by value/weight ratio
    value_per_weight = [v/w for v, w in zip(values, weights)]
    sorted_value_per_weight = sorted(value_per_weight, reverse=True)
    sorted_weights = [w for _, w in sorted(zip(value_per_weight, weights), reverse=True)]
    sorted_values = [v for _, v in sorted(zip(value_per_weight, values), reverse=True)]
    # print(sorted_value_per_weight)
    # print(sorted_weights)
    # print(sorted_values)
    for w, v in zip(sorted_weights, sorted_values):
        if capacity == 0:
            return value
        a = min(w, capacity)
        value += a * (v/w)
        capacity -= a
    # round result to 4 decimal places
    return round(value, 4)

# write test_cases function
def test_cases():
    assert(get_optimal_value(50, [20, 50, 30], [60, 100, 120]) == 180.0)
    assert(get_optimal_value(10, [30], [500]) == 166.6667)
    assert(get_optimal_value(0, [30], [500]) == 0.0)
    assert(get_optimal_value(10, [30], [500]) == 166.6667)


if __name__ == "__main__":
    if DEBUG:
        test_cases()
    else:
        data = list(map(int, sys.stdin.read().split()))
        n, capacity = data[0:2]
        values = data[2:(2 * n + 2):2]
        weights = data[3:(2 * n + 2):2]
        opt_value = get_optimal_value(capacity, weights, values)
        print("{:.4f}".format(opt_value))
