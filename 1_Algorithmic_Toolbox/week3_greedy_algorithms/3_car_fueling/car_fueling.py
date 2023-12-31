# python3
import sys

DEBUG = False

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
    # add the starting point and the destination to the list of stops
    stops = [0] + stops + [distance]

    # iterate through the stops
    while current_refill <= n:
        last_refill = current_refill
        # find the farthest stop that can be reached
        while current_refill <= n and stops[current_refill+1] - stops[last_refill] <= tank:
            current_refill += 1
        # if the farthest stop that can be reached is the same as the last stop that can be reached
        # then it is not possible to reach the destination
        if current_refill == last_refill:
            return -1
        # if the farthest stop that can be reached is not the destination
        # then increment the number of refills
        if current_refill <= n:
            num_refills += 1
    return num_refills

# write test_cases for compute_min_refills(distance, tank, stops)
def test_compute_min_refills():
    """
    Some test cases for compute_min_refills(distance, tank, stops).
    """
    assert compute_min_refills(950, 400, [200, 375, 550, 750]) == 2
    assert compute_min_refills(10, 3, [1, 2, 5, 9]) == -1
    assert compute_min_refills(200, 250, [100, 150]) == 0
    print("Test cases passed...")


if __name__ == '__main__':
    if DEBUG:
        test_compute_min_refills()
    else:
        d, m, _, *stops = map(int, sys.stdin.read().split())
        print(compute_min_refills(d, m, stops))
