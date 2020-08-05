# Kd-Tree
A data type to repreent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1) using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point). 2d-trees have numerous applications, ranging from classifying astronomical objects to computer animation to speeding up neural networks to mining data to image retrieval.
# Implementation
For brute-froce implementation, I write a mutable data type PointSET.java that represents a set of points in the unit square. I used TreeSet as my 2d-tree. For a more efficient implementation, we write a mutable data type KdTree.java that uses a 2d-tree to implement the same API. A 2d-tree is a generalization of a Binary Search Tree to two-dimensional keys. The idea is to build a BST with points in the nodes, using the x- and y-coordinates of the points as keys in strictly alternating sequence.
The prime advantage of a 2d-tree over a BST is that it supports efficient implementation of range search and nearest-neighbor search. Each node corresponds to an axis-aligned rectangle in the unit square, which encloses all of the points in its subtree. The root corresponds to the unit square; the left and right children of the root corresponds to the two rectangles split by the x-coordinate of the point at the root; and so forth.
# Conclusion
Project involves my solution to the kd-tree problem. This problem is one of the assignments given
in the online algorithm course of Princeton University. KdTreeVisualizer.java, RangeSearchVisualizer.java and NearestNeighborVisualizer.java are already given to debug our code. My work consists of KdTree.java and PointSET.java.


# References
* https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php

