# Uses python3
import sys

def get_change(m):
    #write your code here
    COINS = [10, 5, 1]
    m = 0
    for coin in COINS:
        while m >= coin:
            m -= coin
            m += 1
    return m

if __name__ == '__main__':
    m = int(sys.stdin.read())
    print(get_change(m))
