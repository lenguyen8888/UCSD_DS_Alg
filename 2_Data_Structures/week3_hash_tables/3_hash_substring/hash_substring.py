# python3
DEBUG = False
DEBUG_HASH = False

def read_input():
    return (input().rstrip(), input().rstrip())

def print_occurrences(output):
    print(' '.join(map(str, output)))

# implement string hashing with polynomial hash function
# and Rabin-Karp's algorithm to find all occurrences of
# the pattern in the text
# define a large prime
_prime = 1000000007
# define a multiplier
_multiplier = 263
# define a hash function
def poly_hash(s):
    ans = 0
    for c in reversed(s):
        ans = (ans * _multiplier + ord(c)) % _prime
    return ans

# precompute the hashes of all substrings of the text
def precompute_hashes(text, pLen):
    # initialize an empty list of hashes
    hashes = [None] * (len(text) - pLen + 1)
    # compute the hash of the last |pattern| characters of the text
    hashes[len(text) - pLen] = poly_hash(text[len(text) - pLen:])
    # precompute the value of multiplier^(|pattern|) % prime
    factor = 1
    for _ in range(pLen):
        factor = (factor * _multiplier) % _prime
    # iterate through the text, computing the hash of each |pattern| characters
    for i in range(len(text) - pLen - 1, -1, -1):
        # update the hash of the |pattern| characters of the text
        # but don't use pow() because it's slow
        hashes[i] = (_multiplier * hashes[i + 1] + ord(text[i]) - factor * ord(text[i + pLen])) % _prime
        # check hash[i] against poly_hash(text[i:i + pLen])
        if DEBUG_HASH:
            assert(hashes[i] == poly_hash(text[i:i + pLen]))
    # return hashes
    return hashes

def get_occurrences(pattern, text):
    """
    Find all occurrences of a pattern in a text using the Rabin-Karp algorithm.

    Args:
        pattern (str): The pattern to search for.
        text (str): The text to search in.

    Returns:
        list: A list of indices where the pattern occurs in the text.
    
    Examples:
        >>> get_occurrences('aba', 'abacaba')
        [0, 4]
        >>> get_occurrences('Test', 'testTesttesT')
        [4]
        >>> get_occurrences('aaaaa', 'baaaaaaa')
        [1, 2, 3]    
    """
    # initialize an empty list of occurrences
    occurrences = []
    # compute the hash of the pattern
    p_hash = poly_hash(pattern)
    # get the hashes of all substrings of the text
    hashes = precompute_hashes(text, len(pattern))
    # compute the hash of the first |pattern| characters of the text
    pLen = len(pattern)
    tLen = len(text)
    # iterate through the text, checking for a match
    for i in range(tLen - pLen + 1):
        # check if the hashes match
        if p_hash != hashes[i]:
            continue
        # check if the strings match
        if text[i:i + pLen] == pattern:
            # append the index to occurrences
            occurrences.append(i)
    # return occurrences
    return occurrences

# write test_cases
def test_cases():
    assert get_occurrences('aba', 'abacaba') == [0, 4]
    assert get_occurrences('Test', 'testTesttesT') == [4]
    assert get_occurrences('aaaaa', 'baaaaaaa') == [1, 2, 3]
    print('all test cases passed...')

if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        print_occurrences(get_occurrences(*read_input()))

