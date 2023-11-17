# python3
import sys


def compute_min_refills(distance, tank, stops):
    """
    Computes the minimum number of refills needed to travel a given distance with a given tank capacity and a list of stops.

    Args:
        distance (int): The total distance to be traveled.
        tank (int): The capacity of the fuel tank.
        stops (list): A list of distances from the starting point to each stop.

    Returns:
        int: The minimum number of refills needed. Returns -1 if it is not possible to reach the destination.

    Example:
        >>> compute_min_refills(950, 400, [200 375 550 750])
        2
        >>> compute_min_refills(10, 3,,[1 2 5 9])
        -1
        >>> compute_min_refills(200, 250,[100,150])
        0
    """
    num_refills = 0
    current_refill = 0
    n = len(stops)
    stops = [0] + stops + [distance]
    while current_refill <= n:
        last_refill = current_refill
        while current_refill <= n and stops[current_refill+1] - stops[last_refill] <= tank:
            current_refill += 1
        if current_refill == last_refill:
            return -1
        if current_refill <= n:
            num_refills += 1
    return num_refills


if __name__ == '__main__':
    d, m, _, *stops = map(int, sys.stdin.read().split())
    print(compute_min_refills(d, m, stops))
