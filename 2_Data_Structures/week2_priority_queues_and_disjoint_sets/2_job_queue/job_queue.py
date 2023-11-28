# python3
DEBUG = False

from collections import namedtuple

AssignedJob = namedtuple("AssignedJob", ["worker", "started_at"])

# write a compare function for AssignedJob objects that
# compares the started_at attribute
# then if the started_at attributes are equal, compare the worker attribute
def cmpAssignedJob(job1, job2):
    """
    Compares two AssignedJob objects.

    Parameters:
    job1 (AssignedJob): The first AssignedJob object to compare.
    job2 (AssignedJob): The second AssignedJob object to compare.

    Returns:
    int: 1 if job1 > job2, 0 if job1 == job2, -1 if job1 < job2
    """
    # Compare the started_at attribute
    if job1.started_at > job2.started_at:
        return 1
    elif job1.started_at < job2.started_at:
        return -1
    else:
        # if the started_at attributes are equal, compare the worker attribute
        if job1.worker > job2.worker:
            return 1
        elif job1.worker < job2.worker:
            return -1
        else:
            return 0

# Build a min-heap from the given data list of n AssignedJob objects
# and use heap compare on the started_at attribute
def heapify(data, n, i):
    """
    Heapifies the subtree rooted at the given index.

    Parameters:
    data (list): The list of elements to heapify.
    n (int): The size of the heap.
    i (int): The index of the root of the subtree to heapify.

    Returns:
    None
    """
    while True:
        smallest = i
        left = 2 * i + 1
        right = 2 * i + 2

        # Check if left child is smaller than current smallest
        if left < n and cmpAssignedJob(data[smallest], data[left]) > 0:
            smallest = left

        # Check if right child is smaller than current smallest
        if right < n and cmpAssignedJob(data[smallest], data[right]) > 0:
            smallest = right

        # If the smallest is not the current node, swap it with the smallest child
        if smallest != i:
            data[i], data[smallest] = data[smallest], data[i]
            i = smallest  # Move down the tree
        else:
            break


def assign_jobs(n_workers, jobs):
    """
    Assigns jobs to workers using a priority queue.

    Args:
        n_workers (int): The number of workers available.
        jobs (list): A list of job durations.

    Returns:
        list: A list of AssignedJob objects representing the assigned jobs.
    Examples:
        >>> assign_jobs(2, [1, 2, 3, 4, 5])
        [AssignedJob(worker=0, started_at=0)
        , AssignedJob(worker=1, started_at=0)
        , AssignedJob(worker=0, started_at=1)
        , AssignedJob(worker=1, started_at=2)
        , AssignedJob(worker=0, started_at=4)]
        >>> assign_jobs(4, [1] * 20)
        [AssignedJob(worker=0, started_at=0)
        , AssignedJob(worker=1, started_at=0)
        , AssignedJob(worker=2, started_at=0)
        , AssignedJob(worker=3, started_at=0)
        , AssignedJob(worker=0, started_at=1)
        , AssignedJob(worker=1, started_at=1)
        , AssignedJob(worker=2, started_at=1)
        , AssignedJob(worker=3, started_at=1)
        , AssignedJob(worker=0, started_at=2)
        , AssignedJob(worker=1, started_at=2)
        , AssignedJob(worker=2, started_at=2)
        , AssignedJob(worker=3, started_at=2)
        , AssignedJob(worker=0, started_at=3)
        , AssignedJob(worker=1, started_at=3)
        , AssignedJob(worker=2, started_at=3)
        , AssignedJob(worker=3, started_at=3)
        , AssignedJob(worker=0, started_at=4)
        , AssignedJob(worker=1, started_at=4)
        , AssignedJob(worker=2, started_at=4)
        , AssignedJob(worker=3, started_at=4)]

    """
    result = []
    # Create a list of n_workers AssignedJob objects with worker = index and started_at = 0
    # This is the initial state of the min-heap
    workers = [AssignedJob(worker, 0) for worker in range(n_workers)]
    for job in jobs:
        # The root of the heap is the next free worker
        next_worker = workers[0]
        # Add the next job to the result
        result.append(next_worker)
        # Update the started_at attribute of the next free worker
        next_worker = AssignedJob(next_worker.worker, next_worker.started_at + job)
        # Replace the root of the heap with the updated next_worker
        workers[0] = next_worker
        # Heapify the heap
        heapify(workers, n_workers, 0)
        
    return result

# write test_cases
def test_cases():
    assert assign_jobs(2, [1, 2, 3, 4, 5]) == \
        [AssignedJob(worker=0, started_at=0)
        , AssignedJob(worker=1, started_at=0)
        , AssignedJob(worker=0, started_at=1)
        , AssignedJob(worker=1, started_at=2)
        , AssignedJob(worker=0, started_at=4)]
    assert assign_jobs(4, [1] * 20) == \
        [AssignedJob(worker=0, started_at=0)
        , AssignedJob(worker=1, started_at=0)
        , AssignedJob(worker=2, started_at=0)
        , AssignedJob(worker=3, started_at=0)
        
        , AssignedJob(worker=0, started_at=1)
        , AssignedJob(worker=1, started_at=1)
        , AssignedJob(worker=2, started_at=1)
        , AssignedJob(worker=3, started_at=1)
        
        , AssignedJob(worker=0, started_at=2)
        , AssignedJob(worker=1, started_at=2)
        , AssignedJob(worker=2, started_at=2)
        , AssignedJob(worker=3, started_at=2)
        , AssignedJob(worker=0, started_at=3)
        , AssignedJob(worker=1, started_at=3)
        , AssignedJob(worker=2, started_at=3)
        , AssignedJob(worker=3, started_at=3)
        
        , AssignedJob(worker=0, started_at=4)
        , AssignedJob(worker=1, started_at=4)
        , AssignedJob(worker=2, started_at=4)
        , AssignedJob(worker=3, started_at=4)]
    print('All test cases passed.')


def main():
    """
    Main function that assigns jobs to workers and prints the worker and start time for each assigned job.

    Examples:
        >>> python3 job_queue.py
        2 5
        1 2 3 4 5
        0 0
        1 0
        0 1
        1 2
        0 4
        >>> python3 job_queue.py
        4 20
        1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
        0 0
        1 0
        2 0
        3 0
        0 1
        1 1
        2 1
        3 1
        0 2
        1 2
        2 2
        3 2
        0 3
        1 3
        2 3
        3 3
        0 4
        1 4
        2 4
        3 4
    """
    n_workers, n_jobs = map(int, input().split())
    jobs = list(map(int, input().split()))
    assert len(jobs) == n_jobs

    assigned_jobs = assign_jobs(n_workers, jobs)

    for job in assigned_jobs:
        print(job.worker, job.started_at)


if __name__ == "__main__":
    if DEBUG:
        test_cases()
    else:
        main()
