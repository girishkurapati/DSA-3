# Matrix Chain Multiplication Optimization

## 3D Visualization using Interval Dynamic Programming

A Java Spring Boot web application that demonstrates **Matrix Chain Multiplication (MCM)** using **Interval Dynamic Programming** with an interactive 3D visualization.

The project finds the optimal way to parenthesize a chain of matrices so that the total number of scalar multiplications is minimized.

---

##  Project Overview

Matrix Chain Multiplication is a classic Dynamic Programming problem.

When multiplying multiple matrices, the final result remains the same regardless of the order of multiplication, but the **number of scalar multiplications can vary significantly** depending on the parenthesization.

For example:

```text
A1 × A2 × A3
