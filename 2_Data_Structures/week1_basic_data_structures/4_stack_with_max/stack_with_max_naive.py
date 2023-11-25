#python3
import sys
DEBUG = False

class StackWithMax():
    """
    A stack data structure that keeps track of the maximum element in the stack.

    Methods:
    - Push(a): Adds element 'a' to the top of the stack.
    - Pop(): Removes and returns the top element from the stack.
    - Max(): Returns the maximum element in the stack.
    """

    def __init__(self):
        self.__stack = []
        # create a stack to keep track of the maximum element in the stack
        # the maximum element is always at the top of the stack so we can
        # return the maximum element in O(1) time
        self.__max_stack = []

    def Push(self, a):
            """
            Pushes an element onto the stack.

            Args:
                a: The element to be pushed onto the stack.
            """
            self.__stack.append(a)
            # if the maximum stack is empty or the element is greater than the top element
            if not self.__max_stack or a >= self.__max_stack[-1]:
                self.__max_stack.append(a)

    def Pop(self):
        assert(len(self.__stack))
        popped = self.__stack.pop()
        # if the popped element is the maximum element, pop it from the maximum stack
        if popped == self.__max_stack[-1]:
            self.__max_stack.pop()

    def Max(self):
        assert(len(self.__stack))
        # return the maximum element in the maximum stack
        # just the top element of the maximum stack => O(1) time
        return self.__max_stack[-1]

# write test_cases for StackWithMax
def test_cases():
    # Case 1
    stack = StackWithMax()
    stack.Push(2)
    stack.Push(1)
    assert stack.Max() == 2
    stack.Pop()
    assert stack.Max() == 2

    # Case 2
    stack = StackWithMax()
    stack.Push(1)
    stack.Push(2)
    assert stack.Max() == 2
    stack.Pop()
    assert stack.Max() == 1

    # Case 3
    stack = StackWithMax()
    stack.Push(2)
    stack.Push(3)
    stack.Push(9)
    stack.Push(7)
    stack.Push(2)
    assert stack.Max() == 9
    assert stack.Max() == 9
    assert stack.Max() == 9
    stack.Pop()
    assert stack.Max() == 9

    # Case 4
    stack = StackWithMax()
    stack.Push(1)
    stack.Push(7)
    stack.Pop()

    # Case 5
    stack = StackWithMax()
    stack.Push(7)
    stack.Push(1)
    stack.Push(7)
    assert stack.Max() == 7
    stack.Pop()
    assert stack.Max() == 7
    print('All test cases passed.')


if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        stack = StackWithMax()

        num_queries = int(sys.stdin.readline())
        for _ in range(num_queries):
            query = sys.stdin.readline().split()

            if query[0] == "push":
                stack.Push(int(query[1]))
            elif query[0] == "pop":
                stack.Pop()
            elif query[0] == "max":
                print(stack.Max())
            else:
                assert(0)
