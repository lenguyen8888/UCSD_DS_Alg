# python3

import sys

DEBUG = False

class SubStrHash:
	# define a class with a large prime and a string text
	def __init__(self, string, prime, x):
		self.string = string
		self.prime = prime
		self.x = x
		self.table = [None] * (len(string) + 1)
		# build hash table of all substrings of string
		self.table[0] = 0
		for i in range(1, len(string) + 1):
			self.table[i] = (self.x * self.table[i - 1] + ord(string[i - 1])) % self.prime
	def fastPow(self, base, power):
		# return base^power % prime
		result = 1
		while power > 0:
			if power % 2 == 1:
				result = (result * base) % self.prime
			base = (base * base) % self.prime
			power //= 2
		return result

	def hash(self, start, length):
		# return the hash of a substring of string
		y = self.fastPow(self.x, length)
		return (self.table[start + length] - y * self.table[start]) % self.prime

class Solver:
	def __init__(self, s):
		self.s = s
		# define 2 SubStrHash objects
		self.substr1 = SubStrHash(s, 1000000007, 263)
		self.substr2 = SubStrHash(s, 1000000009, 263)
	
	def ask(self, a, b, l):
		# check if substrings of s are equal with substr1 hash
		hash1Match = self.substr1.hash(a, l) == self.substr1.hash(b, l)
		# check if substrings of s are equal with substr2 hash
		hash2Match = self.substr2.hash(a, l) == self.substr2.hash(b, l)
		return hash1Match and hash2Match

# write Solver test_cases
def test_cases():
	# test case 1
	solver = Solver('trololo')
	assert(solver.ask(0, 0, 7) == True)
	assert(solver.ask(2, 4, 3) == True)
	assert(solver.ask(3, 5, 1) == True)
	assert(solver.ask(1, 3, 2) == False)
	# test case 2
	solver = Solver('abacaba')
	assert(solver.ask(0, 0, 7) == True)
	assert(solver.ask(0, 1, 1) == False)
	assert(solver.ask(0, 4, 2) == True)
	assert(solver.ask(0, 1, 3) == False)
	print("test_cases() passed")

if __name__ == '__main__':
	if DEBUG:
		test_cases()
	else:		
		# read in the string s
		s = sys.stdin.readline()
		q = int(sys.stdin.readline())
		solver = Solver(s)
		for i in range(q):
			a, b, l = map(int, sys.stdin.readline().split())
			print("Yes" if solver.ask(a, b, l) else "No")
