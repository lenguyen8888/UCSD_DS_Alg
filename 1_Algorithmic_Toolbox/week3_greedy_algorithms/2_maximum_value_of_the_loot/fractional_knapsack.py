# Uses python3
import sys

def get_optimal_value(capacity, weights, values):
    value = 0.
    # write your code here
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
    return value


if __name__ == "__main__":
    data = list(map(int, sys.stdin.read().split()))
    n, capacity = data[0:2]
    values = data[2:(2 * n + 2):2]
    weights = data[3:(2 * n + 2):2]
    opt_value = get_optimal_value(capacity, weights, values)
    print("{:.10f}".format(opt_value))
