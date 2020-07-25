# Percolation Problem
A program to estimate the value of the percolation threshold using Manto Carlo simulation.
# Percolation
In physics, chemistry and materials science, percolation refers to the movement and filtering of fluids through porous 
materials. Broader applications have since been developed that cover connectivity of many systems modeled as lattices
or graphs, analogous to connectivity of lattice components in the filtration problem that modulates capacity for percolation.
# Approach
We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked. 
A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites.
We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all 
open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic 
materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from 
top to bottom, with full sites conducting. For the porous substance example, the open sites correspond to empty space through 
which water might flow, so that a system that percolates lets water fill open sites, flowing from top to bottom.)
# The Problem 
In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be 
open with probability p (and therefore blocked with probability 1 − p), what is the probability that the system percolates? 
When p equals 0, the system does not percolate; when p equals 1, the system percolates. 

When n is sufficiently large, there is a threshold value p* such that when p < p* a random n-by-n grid almost never percolates, 
and when p > p*, a random n-by-n grid almost always percolates. No mathematical solution for determining the percolation 
threshold p* has yet been derived. Your task is to write a computer program to estimate p*.
# Monte Carlo Simulation
To estimate the percolation threshold, consider the following computational experiment:

* Initialize all sites to be blocked.
  * Repeat the following until the system percolates:
   * Choose a site uniformly at random among all blocked sites.
   * Open the site.
   * The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
# Conclusion
Project involves my solution to the percolation problem. This problem is one of the assignments given
in the online algorithm course of Princeton University. PercolationVisualizer is already given
in the specification of the assignment. My work consists of percolation.java and percolationStats.java.
Two WeightedQuickUnionUF objects are used in order to deal with "backwash" problem.

Bonus Test Case : 0/1 (Total memory test) 

# References
* https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
* https://en.wikipedia.org/wiki/Percolatio
