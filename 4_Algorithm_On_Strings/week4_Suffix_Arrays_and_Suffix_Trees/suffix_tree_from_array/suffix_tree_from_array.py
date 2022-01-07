# python3
import sys
# from _tracemalloc import start
# from curses import start_color

ALPHABET = "$ACGT"
class SuffixTree:
    class Node:
        def __init__(self, node, depth, start, end):
            self.parent = node
            self.children = {}
            self.depth = depth
            self.start = start
            self.end = end
    def __init__(self, text, order, lcp):
        self.text = text
        self.order = order
        self.lcp = lcp
        self.root = self.Node(None, 0, -1, -1)
        self.ele = ['$', 'A', 'C', 'G', 'T']
                
    def STFromSA(self):
        lcpPrev = 0
        curNode = self.root
        for i in range(len(self.text)):
            suffix = self.order[i]
            while curNode.depth > lcpPrev:
                curNode = curNode.parent
            if curNode.depth == lcpPrev:
                curNode = self.CreateNewLeaf(curNode, suffix)
            else:
                edgeStart = self.order[i-1] + curNode.depth
                offset = lcpPrev - curNode.depth
                midNode = self.BreakEdge(curNode, edgeStart, offset)
                curNode = self.CreateNewLeaf(midNode, suffix)
            if i < len(self.text) - 1:
                lcpPrev = self.lcp[i]        
        return self.root  

    def CreateNewLeaf(self, node, suffix):
        textLen = len(self.text)
        leaf = self.Node(node
                , textLen - suffix
                , suffix + node.depth
                , textLen) #, len(s)-1)
        node.children[self.text[leaf.start]] = leaf
        return leaf

    def BreakEdge(self, node, start, offset):
        startChar = self.text[start]
        midChar = self.text[start + offset]
        midNode = self.Node(node
                    ,node.depth + offset
                    ,start
                    ,start + offset) #,start + offset - 1)
        midNode.children[midChar] = node.children[startChar]
        node.children[startChar].parent = midNode
        node.children[startChar].start += offset
        node.children[startChar] = midNode
        return midNode
                  
def suffix_array_to_suffix_tree(sa, lcp, text):
    """
    Build suffix tree of the string text given its suffix array suffix_array
    and LCP array lcp_array. Return the tree as a mapping from a node ID
    to the list of all outgoing edges of the corresponding node. The edges in the
    list must be sorted in the ascending order by the first character of the edge label.
    Root must have node ID = 0, and all other node IDs must be different
    nonnegative integers. Each edge must be represented by a tuple (node, start, end), where
        * node is the node ID of the ending node of the edge
        * start is the starting position (0-based) of the substring of text corresponding to the edge label
        * end is the first position (0-based) after the end of the substring corresponding to the edge label

    For example, if text = "ACACAA$", an edge with label "$" from root to a node with ID 1
    must be represented by a tuple (1, 6, 7). This edge must be present in the list tree[0]
    (corresponding to the root node), and it should be the first edge in the list (because
    it has the smallest first character of all edges outgoing from the root).
    """
    tree = SuffixTree(text, sa, lcp)
    root = tree.STFromSA()
    return root

def printEdges(cur):
    if cur.parent is not None:
        print(cur.start, cur.end)
    for c in ALPHABET:
        child = cur.children.get(c, None)
        if child is not None:
            printEdges(child)


if __name__ == '__main__':
    text = sys.stdin.readline().strip()
    sa = list(map(int, sys.stdin.readline().strip().split()))
    lcp = list(map(int, sys.stdin.readline().strip().split()))
    print(text)
    # Build the suffix tree and get a mapping from 
    # suffix tree node ID to the list of outgoing Edges.
    tree = suffix_array_to_suffix_tree(sa, lcp, text)
    """
    Output the edges of the suffix tree in the required order.
    Note that we use here the contract that the root of the tree
    will have node ID = 0 and that each vector of outgoing edges
    will be sorted by the first character of the corresponding edge label.
    
    The following code avoids recursion to avoid stack overflow issues.
    It uses two stacks to convert recursive function to a while loop.
    This code is an equivalent of 
    
        OutputEdges(tree, 0);
    
    for the following _recursive_ function OutputEdges:
    
    def OutputEdges(tree, node_id):
        edges = tree[node_id]
        for edge in edges:
            print("%d %d" % (edge[1], edge[2]))
            OutputEdges(tree, edge[0]);
    
    """
    # stack = [(0, 0)]
    # result_edges = []
    # while len(stack) > 0:
    #   (node, edge_index) = stack[-1]
    #   stack.pop()
    #   if not node in tree:
    #     continue
    #   edges = tree[node]
    #   if edge_index + 1 < len(edges):
    #     stack.append((node, edge_index + 1))
    #   print("%d %d" % (edges[edge_index][1], edges[edge_index][2]))
    #   stack.append((edges[edge_index][0], 0))
    printEdges(tree)
