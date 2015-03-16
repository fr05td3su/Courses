# version code 3ebd92e7eece+
coursera = 1
# Please fill out this stencil and submit using the provided submission script.

from math import sqrt
from vec import Vec

norm = lambda v: sqrt(v * v)

## 1: (Problem 1) Norm
norm1 = norm(Vec({0, 1, 2}, {0: 2, 1: 2, 2: 1}))
norm2 = norm(Vec({0, 1, 2, 3}, {0: sqrt(2), 1: sqrt(3), 2: sqrt(5), 3: sqrt(6)}))
norm3 = norm(Vec({0, 1, 2, 3, 4, 5, 6, 7, 8}, {0: 1, 1: 1, 2: 1, 3: 1, 4: 1, 5: 1, 6: 1, 7: 1, 8: 1}))

## 2: (Problem 2) Closest Vector
# Write each vector as a list
closest_vector_1 = [1.6, 3.2]
closest_vector_2 = [0, 1, 0]
closest_vector_3 = [3, 2, 1, -4]

## 3: (Problem 3) Projection Orthogonal to and onto Vectors
# Write each vector as a list
# round up to 6 decimal points if necessary
project_onto_1 = [2, 0]
projection_orthogonal_1 = [0, 1]

project_onto_2 = [-1/6, -1/3, 1/6]
projection_orthogonal_2 = [7/6, 4/3, 23/6]

project_onto_3 = [1, 1, 4]
projection_orthogonal_3 = [0, 0, 0]