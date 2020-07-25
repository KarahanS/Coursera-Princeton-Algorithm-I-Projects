# 8 Puzzle
A program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm.
# The Problem
The 8-puzzle is a sliding puzzle that is played on a 3-by-3 grid with 8 square tiles labeled 1 through 8, plus a blank square. The goal is to rearrange the tiles so that they are in row-major order, using as few moves as possible. You are permitted to slide tiles either horizontally or vertically into the blank square. The following diagram shows a sequence of moves from an initial board to the goal board.
# A* Search Algorithm
A* (pronounced "A-star") is a graph traversal and path search algorithm, which is often used in many fields of computer science due to its completeness, optimality, and optimal efficiency. A* is an informed search algorithm, or a best-first search, meaning that it is formulated in terms of weighted graphs: starting from a specific starting node of a graph, it aims to find a path to the given goal node having the smallest cost (least distance travelled, shortest time, etc.). It does this by maintaining a tree of paths originating at the start node and extending those paths one edge at a time until its termination criterion is satisfied.
# Implementation
We define a search node of the game to be a board, the number of moves made to reach the board, and the previous search node. First, insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue. Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node). Repeat this procedure until the search node dequeued corresponds to the goal board.
# Conclusion
Project involves my solution to the slider puzzle problem. This problem is one of the assignments given
in the online algorithm course of Princeton University. PuzzleChecker is already given
in the specification of the assignment. My work consists of Board.java and Solver.java.
MinPQ (already present in the Princeton's java library) data type used for the priority queue.


# References
* https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
* https://en.wikipedia.org/wiki/A*_search_algorithm
