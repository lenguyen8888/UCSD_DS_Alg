# python3

import sys
import threading
DEBUG = False


def compute_height(n, parents):
    """
    Compute the height of a tree given the number of nodes and the list of parents.

    Parameters:
    n (int): The number of nodes in the tree.
    parents (list): The list of parents for each node.

    Returns:
    int: The height of the tree.

    Example:
        >>> compute_height(5, [4, -1, 4, 1, 1])
        3
        >>> compute_height(5, [-1, 0, 4, 0, 3])
        4
    """
    # create a list of heights for each node
    heights = [0] * n
    max_height = 0
    # iterate through each node
    for node in range(n):
        current_height = 0
        # iterate through each parent node
        start_node = node
        while node != -1:
            # if the height of the current node is already computed, break
            if heights[node] != 0:
                current_height += heights[node]
                break
            # if the height of the current node is not computed, increment the height
            current_height += 1
            # move to the parent node
            node = parents[node]
        # update the height start_node if the height is not computed
        if heights[start_node] == 0:
            heights[start_node] = current_height
        # update the height of the current node
        if current_height > max_height:
            max_height = current_height
    # return the maximum height
    return max_height

# write test_cases
def test_cases():
    assert compute_height(5, [4, -1, 4, 1, 1]) == 3
    assert compute_height(5, [-1, 0, 4, 0, 3]) == 4
    print('All test cases passed.')

def main():
    if DEBUG:
        test_cases()
    else:
        n = int(input())
        parents = list(map(int, input().split()))
        print(compute_height(n, parents))


# In Python, the default limit on recursion depth is rather low,
# so raise it here for this problem. Note that to take advantage
# of bigger stack, we have to launch the computation in a new thread.
sys.setrecursionlimit(10**7)  # max depth of recursion
threading.stack_size(2**27)   # new thread will get stack of such size
threading.Thread(target=main).start()
