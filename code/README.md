# 3D Graphic Designing Using Matrix Chain Multiplication

A fully input-driven Java website for demonstrating Matrix Chain Multiplication and related algorithmic analysis using an uploaded image or CSV dataset.

## Inputs
- Image: converted to a grayscale numeric matrix automatically.
- CSV: numeric columns are extracted automatically; text/class/label columns are not required.

## Main analysis
- Input matrix preview and statistics
- Sequence alignment DP table
- KMP, Z-function and Rabin-Karp pattern matching
- Suffix array and LCP analysis
- Matrix Chain Multiplication
- Complete MCM DP cost table
- Optimal parenthesization
- Minimum multiplication cost
- Left-to-right comparison
- Subset/bitmask DP demonstration
- Capacity network and Edmonds-Karp maximum flow

## Run on Windows
1. Open CMD in this folder.
2. Run `mvn clean package`.
3. Run `java -jar target\graphic-designing-mcm-1.0.0.jar`.
4. Open `http://localhost:8080`.

You can also run on another port, for example `java -jar target\graphic-designing-mcm-1.0.0.jar 8090`.
