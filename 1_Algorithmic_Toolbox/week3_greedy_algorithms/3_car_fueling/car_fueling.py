# python3
import sys


def compute_min_refills(distance, tank, stops):
    # write your code here
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
