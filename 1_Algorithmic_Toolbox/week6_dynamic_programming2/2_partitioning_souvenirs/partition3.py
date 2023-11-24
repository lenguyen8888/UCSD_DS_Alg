# Uses python3
import sys
import itertools
DEBUG = False

def partition3(arr):
    possible, dp = partition3_dp(arr)
    return 1 if possible else 0

def partition3_dp(arr):
    total_sum = sum(arr)
    n = len(arr)

    if total_sum % 3 != 0:
        return False, None

    dp = [[[False for _ in range(total_sum + 1)] for _ in range(total_sum + 1)] for _ in range(n + 1)]
    dp[0][0][0] = True

    target = total_sum // 3

    for i in range(1, n + 1):
        for j in range(target + 1):
            for k in range(target + 1):
                dp[i][j][k] = dp[i - 1][j][k]
                if j >= arr[i - 1]:
                    dp[i][j][k] = dp[i][j][k] or dp[i - 1][j - arr[i - 1]][k]
                if k >= arr[i - 1]:
                    dp[i][j][k] = dp[i][j][k] or dp[i - 1][j][k - arr[i - 1]]

    return dp[n][target][target], dp

def partition_back(arr):
    is_possible, dp = partition3_dp(arr)

    if not is_possible:
        return "No partition possible"

    n = len(arr)
    total_sum = sum(arr)
    target = total_sum // 3

    group1 = []
    group2 = []
    group3 = []

    i, j, k = n, target, target

    while i > 0: # and (j > 0 or k > 0):
        if j >= arr[i - 1] and dp[i - 1][j - arr[i - 1]][k]:
            group1.append(arr[i - 1])
            j -= arr[i - 1]
        elif k >= arr[i - 1] and dp[i - 1][j][k - arr[i - 1]]:
            group2.append(arr[i - 1])
            k -= arr[i - 1]
        else:
            group3.append(arr[i - 1])
        i -= 1

    return group1, group2, group3


import random

def generate_list_with_sum(length, total_sum):
    """ Generate a list of `length` integers that add up to `total_sum` """
    if length == 1:
        return [total_sum]
    # Generate a list of length - 1 integers between 1 and (total_sum - 1) // length
    nums = [random.randint(1, (total_sum - 1) // length) for _ in range(length - 1)]
    # Add the last number to make the total sum equal to `total_sum`
    nums.append(total_sum - sum(nums))
    random.shuffle(nums)
    return nums

def generate_random_div_by3_list(max_length=20, value_range=(1, 30)):
    # Ensure the length is a multiple of 3 for equal partitioning
    length = random.randint(1, max_length // 3) * 3
    third_length = length // 3

    # Generate three lists with equal sum
    third_sum = random.randint(value_range[0] * third_length, value_range[1] * third_length)
    list1 = generate_list_with_sum(third_length, third_sum)
    list2 = generate_list_with_sum(third_length, third_sum)
    list3 = generate_list_with_sum(third_length, third_sum)

    combined_list = list1 + list2 + list3
    random.shuffle(combined_list)
    return combined_list

def generate_list_not_div_by3(max_length=20, value_range=(1, 30)):
    nums = generate_random_div_by3_list(max_length, value_range)
    max_val = sum(nums)
    # add max_val + 1 and max_val + 2 to make the sum divisible by 3
    # and the group since these 2 values will be in different groups
    # We can divide the list into 3 groups that have equal sum
    nums.append(max_val + 1)
    nums.append(max_val + 2)
    return nums

def test_cases():
    # generate 10 random list that can be divided into 3 equal sum subsets
    for good_id in range(10):
        nums = generate_random_div_by3_list()
        assert(partition3(nums) == 1)
        print(good_id, nums)
        possible, dp = partition3_dp(nums)
        assert(possible)
        print(partition_back(nums))
    for bad_id in range(10):
        nums = generate_list_not_div_by3()
        print(bad_id, nums)
        assert(partition3(nums) == 0)
    print("Test passed")

if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        input = sys.stdin.read()
        n, *A = list(map(int, input.split()))
        print(partition3(A))

