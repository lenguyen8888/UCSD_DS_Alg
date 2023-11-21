# Uses python3
import sys
DEBUG = False

def fast_count_segments(starts, ends, points):
    """
    Counts the number of segments that contain each point in a given list of points.

    Args:
        starts (list): List of starting points of segments.
        ends (list): List of ending points of segments.
        points (list): List of points to count.

    Returns:
        list: List of counts, where each count represents the number of segments that contain the corresponding point.

    Examples:
        >>> fast_count_segments([0, 7], [5, 10], [1, 6, 11])
        [1, 0, 0]
        >>> fast_count_segments([0, -3, 7], [5, 2, 10], [1, 6])
        [2, 0]

    """
    cnt = [0] * len(points)
    points_type = []
    # 0: start point, 1: position, 2: end point
    for i in range(len(starts)):
        points_type.append((starts[i], 0, i))
    for i in range(len(points)):
        # the third element of the point is the index of 
        # the point in the points list to save time when
        # assigning the count of the point to the cnt list
        points_type.append((points[i], 1, i))
    for i in range(len(ends)):
        points_type.append((ends[i], 2, i))
    
    # sort the points_type list by the first element of each
    # point coordinate and then by the second element (type id, start, point, end)
    points_type.sort(key=lambda x: (x[0], x[1]))
  
    # count segments
    segments = 0

    # iterate over the points_type list using the range of its length
    for i in range(len(points_type)):
        if points_type[i][1] == 0:
            # if the second element of the current point is 0, it is a start point
            # increment the segments count
            segments += 1
        elif points_type[i][1] == 2:
            # if the second element of the current point is 1, it is an end point
            # decrement the segments count
            segments -= 1
        else:
            # if the second element of the current point is 2, it is a point
            # set the count of the point to the current segments count
            cnt[points_type[i][2]] = segments
    return cnt

    

def naive_count_segments(starts, ends, points):
    cnt = [0] * len(points)
    for i in range(len(points)):
        for j in range(len(starts)):
            if starts[j] <= points[i] <= ends[j]:
                cnt[i] += 1
    return cnt
# write test_cases
def test_cases():
    assert fast_count_segments([0, 7], [5, 10], [1, 6, 11]) == [1, 0, 0]
    assert fast_count_segments([0, -3, 7], [5, 2, 10], [1, 6]) == [2, 0]
    print("Test passed...")

if __name__ == '__main__':
    if DEBUG:
        test_cases()

    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n = data[0]
    m = data[1]
    starts = data[2:2 * n + 2:2]
    ends   = data[3:2 * n + 2:2]
    points = data[2 * n + 2:]
    #use fast_count_segments
    cnt = fast_count_segments(starts, ends, points)
    for x in cnt:
        print(x, end=' ')
