# Collinear Points-Princeton
A program to recognize line patterns in a given set of points.
# Pattern Recognition
Computer vision involves analyzing patterns in visual images and reconstructing the real-world objects that produced them. The process is often broken up into two phases: feature detection and pattern recognition. Feature detection involves selecting important features of the image; pattern recognition involves discovering patterns in the features. This project will investigate a particularly clean pattern recognition problem involving points and line segments. This kind of pattern recognition arises in many other applications such as statistical data analysis.
# The Problem 
Given a set of n distinct points in the plane, find every (maximal) line segment that connects a subset of 4 or more of the points.

# Conclusion
Project involves my solution to the collinear points problem. This problem is one of the assignments given
in the online algorithm course of Princeton University. LineSegment.java is already given
in the specification of the assignment. My work consists of Point.java and BruteCollinearPoint.java and 
FastCollinearPoint.java. I used QuickSort in brute force approach and MergeSort in fast (sorting-based) 
algorithm since it requires a stable sorting algorithm.


# References
* https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php
