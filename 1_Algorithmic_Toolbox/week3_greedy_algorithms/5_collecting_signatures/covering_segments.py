# Uses python3
import sys
from collections import namedtuple
from collections import namedtuple


# Importing the namedtuple class from the collections module

# Defining a named tuple called Segment with fields 'start' and 'end'
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

    # Initialize an empty list to store the optimal points
    points = []

    # Sort the segments in ascending order based on the end point
    segments.sort(key=lambda x: x.end)

    # Initialize the current point with the end point of the first segment
    current = segments[0].end

    # Add the current point to the list of points
    points.append(current)

    # Iterate through the segments
    for s in segments:
        # If the current point is not within the segment
        if current < s.start or current > s.end:
            # Update the current point to the end point of the current segment
            current = s.end
            # Add the current point to the list of points
            points.append(current)

    # Return the list of optimal points
    return points


if __name__ == '__main__':
    input = sys.stdin.read()
    n, *data = map(int, input.split())
    segments = list(map(lambda x: Segment(x[0], x[1]), zip(data[::2], data[1::2])))
    points = optimal_points(segments)
    print(len(points))
    print(*points)
