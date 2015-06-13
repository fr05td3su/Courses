"""Well this all looks awful. Thanks to the CodeSkulptor, 
which does not even support dictionary comprehension, due
to the old version of Python. This is, btw, the first programming
exercice in the AT Course at Coursera.
"""
EX_GRAPH0 = {0:set([1,2]),
             1:set([]),
             2:set([])}
EX_GRAPH1 = {0:set([1,4,5]),
             1:set([2,6]),
             2:set([3]),
             3:set([0]),
             4:set([1]),
             5:set([2]),
             6:set([])}
EX_GRAPH2 = {0:set([4,5,1]),
             1:set([6,2]),
             2:set([7,3]),
             3:set([7]),
             4:set([1]),
             5:set([2]),
             6:set([]),
             7:set([3]),
             8:set([1,2]),
             9:set([0,4,5,6,7,3])}


def make_complete_graph(num_nodes):
    """Takes the number of nodes num_nodes and returns a dictionary 
    corresponding to a complete directed graph with the 
    specified number of nodes. 
    """
    return dict((num , set([dummy for dummy in range(num_nodes) if dummy != num])) for num in range(num_nodes))

def compute_in_degrees(digraph):
    """Takes a directed graph digraph (represented as a dictionary) 
    and computes the in-degrees for the nodes in the graph. 
    """
    return dict((key , sum([1 for dummy in digraph.values() if key in dummy])) for key in digraph.keys())

def in_degree_distribution(digraph):
    """Takes a directed graph digraph (represented as a dictionary)
    and computes the unnormalized distribution of the in-degrees
    of the graph. 
    """
    distribution = {}
    for indegree in compute_in_degrees(digraph).values():
        if indegree not in distribution.keys():   
            distribution[indegree] = 0 
        distribution[indegree] += 1
    return distribution
    
