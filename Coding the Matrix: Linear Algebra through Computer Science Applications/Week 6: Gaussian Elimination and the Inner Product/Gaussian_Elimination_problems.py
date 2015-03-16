# version code 62505f329d9b
coursera = 1
# Please fill out this stencil and submit using the provided submission script.

from matutil import *
from vecutil import zero_vec
from GF2 import one

## 1: (Problem 1) Recognizing Echelon Form
# Write each matrix as a list of row lists

echelon_form_1 = [[1,2,0,2,0],
                  [0,1,0,3,4],
                  [0,0,2,3,4],
                  [0,0,0,2,0],
                  [0,0,0,0,4]]
echelon_form_2 = [[0,4,3,4,4],
                  [0,0,4,2,0],
                  [0,0,0,0,1],
                  [0,0,0,0,0]]
echelon_form_3 = [[1,0,0,1],
                  [0,0,0,1],
                  [0,0,0,0]]
echelon_form_4 = [[1,0,0,0],
                  [0,1,0,0],
                  [0,0,0,0],
                  [0,0,0,0]]

## 2: (Problem 2) Is it echelon?
def is_echelon(A):
    '''
    Input:
        - A: a list of row lists
    Output:
        - True if A is in echelon form
        - False otherwise
    Examples:
        >>> is_echelon([[1,1,1],[0,1,1],[0,0,1]])
        True
        >>> is_echelon([[0,1,1],[0,1,0],[0,0,1]])
        False
        >>> is_echelon([[1,1]])
        True
        >>> is_echelon([[1]])
        True
        >>> is_echelon([[1],[1]])
        False
        >>> is_echelon([[0]])
        True
        >>> is_echelon([[0],[1]])
        False
    '''
    last_pivot = -1
    for i in range(len(A)):
        pivot = last_pivot
        for j in range(len(A[i])):
            if A[i][j] != 0:
                pivot = j
                if pivot <= last_pivot:
                    return False
                else:
                    break
            else:
                pivot += 1
        last_pivot = pivot
    return True

## 3: (Problem 3) Solving with Echelon Form: No Zero Rows
# Give each answer as a list

echelon_form_vec_a = [1,0,3,0]
echelon_form_vec_b = [-3,0,-2,3]
echelon_form_vec_c = [-5,0,2,0,2]

## 4: (Problem 4) Solving with Echelon Form
# If a solution exists, give it as a list vector.
# If no solution exists, provide "None" (without the quotes).

solving_with_echelon_form_a = None
solving_with_echelon_form_b = [21,0,2,0,0]



## 5: (Problem 5) Echelon Solver
def echelon_solve(row_list, label_list, b):
    '''
    Input:
        - row_list: a list of Vecs
        - label_list: a list of labels establishing an order on the domain of
                      Vecs in row_list
        - b: a vector (represented as a list)
    Output:
        - Vec x such that row_list * x is b
    >>> D = {'A','B','C','D','E'}
    >>> U_rows = [Vec(D, {'A':one, 'E':one}), Vec(D, {'B':one, 'E':one}), Vec(D,{'C':one})]
    >>> b_list = [one,0,one]
    >>> cols = ['A', 'B', 'C', 'D', 'E']
    >>> echelon_solve(U_rows, cols, b_list) == Vec({'B', 'C', 'A', 'D', 'E'},{'B': 0, 'C': one, 'A': one})
    True
    '''
    x = Vec(set(label_list),{})
    for i in range(len(row_list)-1,-1,-1):
        for key in label_list:
            if row_list[i][key] != 0:
                x[key] = b[i] - row_list[i]*x
                break
    return x

## 6: (Problem 6) Solving General Matrices via Echelon
row_list = [Vec({'A', 'C', 'B', 'D'},{'A': one, 'C': 0, 'B': one, 'D': one}), Vec({'A', 'C', 'B', 'D'},{'A': 0, 'C': 0, 'B': one, 'D': 0}), Vec({'A', 'C', 'B', 'D'},{'A': 0, 'C': one, 'B': 0, 'D': 0}), Vec({'A', 'C', 'B', 'D'},{'A': 0, 'C': 0, 'B': 0, 'D': one})]
# Provide as a list of Vec instances
label_list = ['A', 'B', 'C', 'D'] # Provide as a list
b = [one,one,0,0] # Provide as a list

## 7: (Problem 7) Nullspace A
null_space_rows_a = {3,4} # Put the row numbers of M from the PDF

## 8: (Problem 8) Nullspace B
null_space_rows_b = {4} # Put the row numbers of M from the PDF

