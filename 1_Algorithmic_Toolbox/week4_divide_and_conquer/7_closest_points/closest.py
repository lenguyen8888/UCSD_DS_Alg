#Uses python3
import sys
import math

DEBUG = False
# implemnt min_distance_brute on array of points (x, y)
def min_distance_brute(points):
    """
    Finds the minimum distance between two points in a given array of points.

    Args:
        points (list): List of points.

    Returns:
        float: Minimum distance between two points.

    Examples:
        >>> min_distance_brute([(0, 0), (3, 4)])
        5.0
        >>> min_distance_brute([(7, 1), (4, 100), (7, 8), (7, 7)])
        0.0

    """
    # initialize min_dist to infinity
    min_dist = float('inf')
    # iterate over all points
    for i in range(len(points)):
        for j in range(i + 1, len(points)):
            # calculate the distance between the two points
            dist = math.sqrt((points[i][0] - points[j][0])**2 + (points[i][1] - points[j][1])**2)
            # update min_dist if the distance is smaller
            if dist < min_dist:
                min_dist = dist
    return min_dist

# implement min_distance_dc on array of points (x, y) using divide and conquer
def min_distance_dc(points):
    """
    Finds the minimum distance between two points in a given array of points.

    Args:
        points (list): List of points.

    Returns:
        float: Minimum distance between two points.

    Examples:
        >>> min_distance_dc([(0, 0), (3, 4)])
        5.0
        >>> min_distance_dc([(7, 7), (1, 100), (4, 8), (7, 7)])
        0.0
        >>> min_distance_dc([(4, 4), (-2,-2), (-3, -4), (-1, 3), (2, 3)
        , (-4, 0), (1, 1), (-1, 1), (3, -1), (-4, 2), (-2, 4)])
        1.4142135623730951
    """
    # if there are less than 4 points, use the brute force algorithm
    if len(points) <= 3:
        return min_distance_brute(points)
    
    # sort the points by their x coordinate
    points.sort(key=lambda x: x[0])
    # find the middle index
    mid = len(points) // 2
    # find the minimum distance of the left half of the points
    min_dist_left = min_distance_dc(points[:mid])
    # find the minimum distance of the right half of the points
    min_dist_right = min_distance_dc(points[mid:])
    # find the minimum distance between the two halves
    min_dist = min(min_dist_left, min_dist_right)
    # find the points that are within the minimum distance from the middle line
    # and sort them by their y coordinate
    strip = sorted([p for p in points if abs(p[0] - points[mid][0]) < min_dist], key=lambda x: x[1])
    # iterate over the points in the strip
    for i in range(len(strip)):
        # iterate over the next 7 points in the strip
        for j in range(i + 1, min(i + 7, len(strip))):
            # calculate the distance between the two points
            dist = math.sqrt((strip[i][0] - strip[j][0])**2 + (strip[i][1] - strip[j][1])**2)
            # update min_dist if the distance is smaller
            if dist < min_dist:
                min_dist = dist
    return min_dist
# write test_cases
def test_cases():
    assert min_distance_dc([(0, 0), (3, 4)]) == 5.0
    assert min_distance_dc([(7, 7), (1, 100), (4, 8), (7, 7)]) == 0.0
    assert min_distance_dc([(4, 4), (-2,-2), (-3, -4), (-1, 3), (2, 3)
        , (-4, 0), (1, 1), (-1, 1), (3, -1), (-4, 2), (-2, 4)]) == 1.4142135623730951

    print("Test passed...")

if __name__ == '__main__':
    if DEBUG:
        test_cases()

    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n = data[0]
    x = data[1::2]
    y = data[2::2]
    # zip the x and y coordinates into a list of points
    points = list(zip(x, y))
    print("{0:.9f}".format(min_distance_dc(points)))
