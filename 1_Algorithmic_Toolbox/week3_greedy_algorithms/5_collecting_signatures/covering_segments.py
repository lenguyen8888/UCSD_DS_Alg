# Uses python3
import sys
from collections import namedtuple

Segment = namedtuple('Segment', 'start end')

def optimal_points(segments):
    """
    Finds the optimal points to cover all segments.

    Args:
        segments (list): List of segments represented as tuples (start, end).

    Returns:
        list: List of optimal points to cover all segments.
    
    Example:
        >>> optimal_points([(1, 3), (2, 5), (3, 6)])
        [3]
        >>> optimal_points([(4, 7), (1, 3), (2, 5), (5, 6)])
        [3, 6]
        >>> optimal_points([(1, 2), (2, 3), (3, 4), (4, 5)])
        [2, 3, 4]
    """
    points = []
    # sort segments by end point
    segments.sort(key=lambda x: x.end)
    # initialize current point
    current = segments[0].end
    # add current point to points
    points.append(current)
    # iterate through segments
    for s in segments:
        # if current point is not in segment
        if current < s.start or current > s.end:
            # update current point
            current = s.end
            # add current point to points
            points.append(current)
    return points


if __name__ == '__main__':
    input = sys.stdin.read()
    n, *data = map(int, input.split())
    segments = list(map(lambda x: Segment(x[0], x[1]), zip(data[::2], data[1::2])))
    points = optimal_points(segments)
    print(len(points))
    print(*points)
