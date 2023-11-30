# python3
DEBUG = False

class Query:
    def __init__(self, query):
        self.type = query[0]
        self.number = int(query[1])
        if self.type == 'add':
            self.name = query[2]

def read_queries():
    n = int(input())
    return [Query(input().split()) for i in range(n)]

def write_responses(result):
    print('\n'.join(result))

def process_queries(queries):
    """
    Process a list of queries and perform operations on a phone book.

    Args:
        queries (list): A list of Query objects representing the operations to be performed.

    Returns:
        list: A list of responses corresponding to each query.
    
    Examples:
        >>> process_queries([Query(['add', '911', 'police'])
        , Query(['add', '76213', 'Mom'])
        , Query(['add', '17239', 'Bob'])
        , Query(['find', '76213'])
        , Query(['find', '910'])
        
        , Query(['find', '911'])
        , Query(['del', '910'])
        , Query(['del', '911'])
        , Query(['find', '911'])
        , Query(['find', '76213'])
        
        , Query(['add', '76213', 'daddy'])
        , Query(['find', '76213'])])
        ['Mom'
        , 'not found'
        , 'police'
        , 'not found'
        , 'Mom'
        , 'daddy']
        >>> process_queries([Query(['find', '3839442'])
        , Query(['add', '123456', 'me'])
        , Query(['add', '0', 'granny'])
        , Query(['find', '0'])
        , Query(['find', '123456'])
        
        , Query(['del', '0'])
        , Query(['del', '0'])
        , Query(['find', '0'])])
        ['not found'
        , 'granny'
        , 'me'
        , 'not found']
    """
    result = []
    # Keep dictionary of all existing (i.e. not deleted yet) contacts.
    contacts = {}
    for cur_query in queries:
        if cur_query.type == 'add':
            # if we already have contact with such number,
            # we should rewrite contact's name
            contacts[cur_query.number] = cur_query.name
        elif cur_query.type == 'del':
            if cur_query.number in contacts:
                del contacts[cur_query.number]
        else:
            response = contacts.get(cur_query.number, 'not found')
            result.append(response)
    return result

# write test_cases
def test_cases():
    assert process_queries([Query(['add', '911', 'police'])
        , Query(['add', '76213', 'Mom'])
        , Query(['add', '17239', 'Bob'])
        , Query(['find', '76213'])
        , Query(['find', '910'])
        
        , Query(['find', '911'])
        , Query(['del', '910'])
        , Query(['del', '911'])
        , Query(['find', '911'])
        , Query(['find', '76213'])
        
        , Query(['add', '76213', 'daddy'])
        , Query(['find', '76213'])]) == \
        ['Mom'
        , 'not found'
        , 'police'
        , 'not found'
        , 'Mom'
        , 'daddy']
    assert process_queries([Query(['find', '3839442'])
        , Query(['add', '123456', 'me'])
        , Query(['add', '0', 'granny'])
        , Query(['find', '0'])
        , Query(['find', '123456'])
        
        , Query(['del', '0'])
        , Query(['del', '0'])
        , Query(['find', '0'])]) == \
        ['not found'
        , 'granny'
        , 'me'
        , 'not found']
    print('Test cases passed...')

if __name__ == '__main__':
    if DEBUG:
        test_cases()
    else:
        write_responses(process_queries(read_queries()))

