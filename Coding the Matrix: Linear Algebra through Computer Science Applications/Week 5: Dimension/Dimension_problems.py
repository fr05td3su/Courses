# version code 60bf7e819480+
coursera = 1
# Please fill out this stencil and submit using the provided submission script.

from vecutil import list2vec
from solver import solve
from matutil import *
from mat import Mat
from GF2 import one
from vec import Vec
from independence import *
from triangular import *

# Some helper functions from hw4
def is_superfluous(L, i):
    assert (i in range(len(L)))
    if len(L) == 1:
        return False
    copyMat = list(L)
    bVec = copyMat.pop(i)   ## deepcopy
    bMat = coldict2mat(copyMat)
    sol = solve(bMat,bVec)
    residual = bVec - bMat * sol
    result = residual * residual
    if result == one:
        return False
    return True if residual * residual < 1.0e-14 else False

def exchange(S, A, z):
    for i in range(len(S)):
        w = S[i]
        if w not in A:
            tmpL = list(S)
            tmpV = tmpL.pop(i)
            tmpL.insert(i,z)
            tmpMat = coldict2mat(tmpL)
            sol = solve(tmpMat,tmpV)
            residual = tmpV - tmpMat * sol
            if residual * residual < 1.0e-14:
                return w
    return None

## 1: (Problem 6.7.2) Iterative Exchange Lemma
w0 = list2vec([1,0,0])
w1 = list2vec([0,1,0])
w2 = list2vec([0,0,1])

v0 = list2vec([1,2,3])
v1 = list2vec([1,3,3])
v2 = list2vec([0,3,3])

# Fill in exchange_S1 and exchange_S2
# with appropriate lists of 3 vectors

exchange_S0 = [w0, w1, w2]
exchange_S1 = [v0, w1, w2]
exchange_S2 = [v0, v1, w2]
exchange_S3 = [v0, v1, v2]


## 2: (Problem 6.7.3) Another Iterative Exchange Lemma
w0 = list2vec([0,one,0])
w1 = list2vec([0,0,one])
w2 = list2vec([one,one,one])

v0 = list2vec([one,0,one])
v1 = list2vec([one,0,0])
v2 = list2vec([one,one,0])

exchange_2_S0 = [w0, w1, w2]
exchange_2_S1 = [v0, w1, w2]
exchange_2_S2 = [v0, v1, w2]
exchange_2_S3 = [v0, v1, v2]


## 3: (Problem 6.7.4) Morph Lemma Coding
def morph(S, B):
    '''
    Input:
        - S: a list of distinct Vec instances
        - B: a list of linearly independent Vec instances
        - Span S == Span B
    Output: a list of pairs of vectors to inject and eject
    Example:
        >>> #This is how our morph works.  Yours may yield different results.
        >>> S = [list2vec(v) for v in [[1,0,0],[0,1,0],[0,0,1]]]
        >>> B = [list2vec(v) for v in [[1,1,0],[0,1,1],[1,0,1]]]
        >>> morph(S, B)
        [(Vec({0, 1, 2},{0: 1, 1: 1, 2: 0}), Vec({0, 1, 2},{0: 1, 1: 0, 2: 0})), (Vec({0, 1, 2},{0: 0, 1: 1, 2: 1}), Vec({0, 1, 2},{0: 0, 1: 1, 2: 0})), (Vec({0, 1, 2},{0: 1, 1: 0, 2: 1}), Vec({0, 1, 2},{0: 0, 1: 0, 2: 1}))]

    '''
    A = []
    copyS = list(S)
    result = []
    for z in B:
        w = exchange(copyS,A,z)
        result.append((z,w))
        copyS.remove(w)
        copyS.append(z)
        A.append(w)
    return result


## 4: (Problem 6.7.5) Row and Column Rank Practice
# Please express each solution as a list of Vecs

row_space_1 = [list2vec([1,2,0]),list2vec([0,2,1])]
col_space_1 = [list2vec([1,0]),list2vec([0,1])]

row_space_2 = [list2vec([1,4,0,0]),list2vec([0,2,2,0]),list2vec([0,0,1,1])]
col_space_2 = [list2vec([1,0,0]),list2vec([0,2,1]),list2vec([0,0,1])]

row_space_3 = [list2vec([1])]
col_space_3 = [list2vec([1,2,3])]

row_space_4 = [list2vec([1,0]), list2vec([0,1])]
col_space_4 = [list2vec([1,2,3]), list2vec([0,1,4])]


## 5: () Subset Basis
def subset_basis(T):
    '''
    Input:
        - T: a set of Vecs
    Output: 
        - set S containing Vecs from T that is a basis for Span T.
    Examples:
        The following tests use the procedure is_independent, provided in module independence
        
        >>> from vec import Vec
        >>> from independence import is_independent
        >>> a0 = Vec({'a','b','c','d'}, {'a':1})
        >>> a1 = Vec({'a','b','c','d'}, {'b':1})
        >>> a2 = Vec({'a','b','c','d'}, {'c':1})
        >>> a3 = Vec({'a','b','c','d'}, {'a':1,'c':3})
        >>> sb = subset_basis({a0, a1, a2, a3})
        >>> len(sb)
        3
        >>> all(v in [a0, a1, a2, a3] for v in sb)
        True
        >>> is_independent(sb)
        True

        >>> b0 = Vec({0,1,2,3},{0:2,1:2,3:4})
        >>> b1 = Vec({0,1,2,3},{0:1,1:1})
        >>> b2 = Vec({0,1,2,3},{2:3,3:4})
        >>> b3 = Vec({0,1,2,3},{3:3})
        >>> sb = subset_basis({b0, b1, b2, b3})
        >>> len(sb)
        3
        >>> all(v in [b0, b1, b2, b3] for v in sb)
        True
        >>> is_independent(sb)
        True

        >>> D = {'a','b','c','d'}
        >>> c0, c1, c2, c3, c4 = Vec(D,{'d': one, 'c': one}), Vec(D,{'d': one, 'a': one, 'c': one, 'b': one}), Vec(D,{'a': one}), Vec(D,{}), Vec(D,{'d': one, 'a': one, 'b': one})
        >>> subset_basis({c0,c1,c2,c3,c4}) == {c0,c1,c2,c4}
        True
    '''
    result = []
    for w in T:
        result.append(w)
        if not is_independent(result):
            result.pop()
    return set(result)


## 6: () Superset Basis Lemma in Python
def superset_basis(C, T):
    '''
    Input:
        - C: linearly independent set of Vecs
        - T: set of Vecs such that every Vec in S is in Span(T)
    Output:
        Linearly independent set S consisting of all Vecs in C and some in T
        such that the span of S is the span of T (i.e. S is a basis for the span
        of T).
    Example:
        >>> from vec import Vec
        >>> from independence import is_independent
        >>> a0 = Vec({'a','b','c','d'}, {'a':1})
        >>> a1 = Vec({'a','b','c','d'}, {'b':1})
        >>> a2 = Vec({'a','b','c','d'}, {'c':1})
        >>> a3 = Vec({'a','b','c','d'}, {'a':1,'c':3})
        >>> sb = superset_basis({a0, a3}, {a0, a1, a2})
        >>> a0 in sb and a3 in sb
        True
        >>> is_independent(sb)
        True
        >>> all(x in [a0,a1,a2,a3] for x in sb)
        True
    '''
    L = list(C)
    for element in T:
        L.append(element)
        if not is_independent(L):
            L.pop() 
    return set(L)


## 7: (Problem 6.7.6) My Is Independent Procedure
def my_is_independent(L):
    '''
    Input:
        - L: a list of Vecs
    Output:
        - boolean: true if the list is linearly independent
    Examples:
        >>> D = {0, 1, 2}
        >>> L = [Vec(D,{0: 1}), Vec(D,{1: 1}), Vec(D,{2: 1}), Vec(D,{0: 1, 1: 1, 2: 1}), Vec(D,{0: 1, 1: 1}), Vec(D,{1: 1, 2: 1})]
        >>> my_is_independent(L)
        False
        >>> my_is_independent(L[:2])
        True
        >>> my_is_independent(L[:3])
        True
        >>> my_is_independent(L[1:4])
        True
        >>> my_is_independent(L[0:4])
        False
        >>> my_is_independent(L[2:])
        False
        >>> my_is_independent(L[2:5])
        False
        >>> L == [Vec(D,{0: 1}), Vec(D,{1: 1}), Vec(D,{2: 1}), Vec(D,{0: 1, 1: 1, 2: 1}), Vec(D,{0: 1, 1: 1}), Vec(D,{1: 1, 2: 1})]
        True
    '''
    return True if rank(L) == len(L) else False


## 8: (Problem 6.7.7) My Rank
def my_rank(L):
    '''
    Input: 
        - L: a list of Vecs
    Output: 
        - the rank of the list of Vecs
    Example:
        >>> L = [list2vec(v) for v in [[1,2,3],[4,5,6],[1.1,1.1,1.1]]]
        >>> my_rank(L)
        2
        >>> L == [list2vec(v) for v in [[1,2,3],[4,5,6],[1.1,1.1,1.1]]]
        True
        >>> my_rank([list2vec(v) for v in [[1,1,1],[2,2,2],[3,3,3],[4,4,4],[123,432,123]]])
        2
    '''
    return len(subset_basis(L))


## 9: (Problem 6.7.11) Direct Sum Unique Representation
def direct_sum_decompose(U_basis, V_basis, w):
    '''
    Input:
        - U_basis: a list of Vecs forming a basis for a vector space U
        - V_basis: a list of Vecs forming a basis for a vector space V
        - w: a Vec in the direct sum of U and V
    Output:
        - a pair (u, v) such that u + v = w, u is in U, v is in V
    Example:

        >>> D = {0,1,2,3,4,5}
        >>> U_basis = [Vec(D,{0: 2, 1: 1, 2: 0, 3: 0, 4: 6, 5: 0}), Vec(D,{0: 11, 1: 5, 2: 0, 3: 0, 4: 1, 5: 0}), Vec(D,{0: 3, 1: 1.5, 2: 0, 3: 0, 4: 7.5, 5: 0})]
        >>> V_basis = [Vec(D,{0: 0, 1: 0, 2: 7, 3: 0, 4: 0, 5: 1}), Vec(D,{0: 0, 1: 0, 2: 15, 3: 0, 4: 0, 5: 2})]
        >>> w = Vec(D,{0: 2, 1: 5, 2: 0, 3: 0, 4: 1, 5: 0})
        >>> (u, v) = direct_sum_decompose(U_basis, V_basis, w)
        >>> (u + v - w).is_almost_zero()
        True
        >>> U_matrix = coldict2mat(U_basis)
        >>> V_matrix = coldict2mat(V_basis)
        >>> (u - U_matrix*solve(U_matrix, u)).is_almost_zero()
        True
        >>> (v - V_matrix*solve(V_matrix, v)).is_almost_zero()
        True
        >>> ww = Vec(D,{0: 2, 1: 5, 2: 51, 4: 1, 5: 7})
        >>> (u, v) = direct_sum_decompose(U_basis, V_basis, ww)
        >>> (u + v - ww).is_almost_zero()
        True
        >>> (u - U_matrix*solve(U_matrix, u)).is_almost_zero()
        True
        >>> (v - V_matrix*solve(V_matrix, v)).is_almost_zero()
        True
        >>> U_basis == [Vec(D,{0: 2, 1: 1, 2: 0, 3: 0, 4: 6, 5: 0}), Vec(D,{0: 11, 1: 5, 2: 0, 3: 0, 4: 1, 5: 0}), Vec(D,{0: 3, 1: 1.5, 2: 0, 3: 0, 4: 7.5, 5: 0})]
        True
        >>> V_basis == [Vec(D,{0: 0, 1: 0, 2: 7, 3: 0, 4: 0, 5: 1}), Vec(D,{0: 0, 1: 0, 2: 15, 3: 0, 4: 0, 5: 2})]
        True
        >>> w == Vec(D,{0: 2, 1: 5, 2: 0, 3: 0, 4: 1, 5: 0})
        True
    '''
    UV = coldict2mat(U_basis + V_basis)
    W = solve(UV, w)
    U = coldict2mat(U_basis)
    V = coldict2mat(V_basis)
    Wu = Vec(set(range(len(U_basis))),{x: W[x] for x in range(len(U_basis))})
    Wv = Vec(set(range(len(V_basis))),{x: W[len(U_basis) + x] for x in range(len(V_basis))})
    u = U * Wu
    v = V * Wv
    return (u,v) 



## 10: (Problem 6.7.12) Is Invertible Function
def is_invertible(M):
    '''
    input: A matrix, M
    outpit: A boolean indicating if M is invertible.

    >>> M = Mat(({0, 1, 2, 3}, {0, 1, 2, 3}), {(0, 1): 0, (1, 2): 1, (3, 2): 0, (0, 0): 1, (3, 3): 4, (3, 0): 0, (3, 1): 0, (1, 1): 2, (2, 1): 0, (0, 2): 1, (2, 0): 0, (1, 3): 0, (2, 3): 1, (2, 2): 3, (1, 0): 0, (0, 3): 0})
    >>> is_invertible(M)
    True

    >>> M1 = Mat(({0,1,2},{0,1,2}),{(0,0):1,(0,2):2,(1,2):3,(2,2):4})
    >>> is_invertible(M1)
    False
    '''
    col = list(mat2coldict(M).values())
    row = list(mat2rowdict(M).values())
    return rank(col) == len(col) and rank(row) == len(row)



## 11: (Problem 6.7.13) Inverse of a Matrix over GF(2)
def find_matrix_inverse(A):
    '''
    Input:
        - A: an invertible Mat over GF(2)
    Output:
        - A Mat that is the inverse of A
    Examples:
        >>> M1 = Mat(({0,1,2}, {0,1,2}), {(0, 1): one, (1, 0): one, (2, 2): one})
        >>> find_matrix_inverse(M1) == Mat(M1.D, {(0, 1): one, (1, 0): one, (2, 2): one})
        True
        >>> M2 = Mat(({0,1,2,3},{0,1,2,3}),{(0,1):one,(1,0):one,(2,2):one})
        >>> find_matrix_inverse(M2) == Mat(M2.D, {(0, 1): one, (1, 0): one, (2, 2): one})
        True
    '''
    R = mat2rowdict(identity(A.D[0], one))
    return coldict2mat([solve(A, R[i]) for i in range(len(R))])



## 12: (Problem 6.7.14) Inverse of a Triangular Matrix
def find_triangular_matrix_inverse(A):
    '''
    Supporting GF2 is not required.

    Input:
        - A: an upper triangular Mat with nonzero diagonal elements
    Output:
        - Mat that is the inverse of A
    
    Example:
        >>> A = listlist2mat([[1, .5, .2, 4],[0, 1, .3, .9],[0,0,1,.1],[0,0,0,1]])
        >>> find_triangular_matrix_inverse(A) == Mat(({0, 1, 2, 3}, {0, 1, 2, 3}), {(0, 1): -0.5, (1, 2): -0.3, (3, 2): 0.0, (0, 0): 1.0, (3, 3): 1.0, (3, 0): 0.0, (3, 1): 0.0, (2, 1): 0.0, (0, 2): -0.05000000000000002, (2, 0): 0.0, (1, 3): -0.87, (2, 3): -0.1, (2, 2): 1.0, (1, 0): 0.0, (0, 3): -3.545, (1, 1): 1.0})
        True
    '''
    R = mat2rowdict(identity(A.D[0], 1))
    return coldict2mat([solve(A, R[i]) for i in range(len(R))])
