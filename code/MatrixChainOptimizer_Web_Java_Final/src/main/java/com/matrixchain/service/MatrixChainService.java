package com.matrixchain.service;

import com.matrixchain.model.OptimizationResult;
import org.springframework.stereotype.Service;

@Service
public class MatrixChainService {

    public OptimizationResult optimize(int[] p) {

        if (p == null || p.length < 2) {
            throw new IllegalArgumentException(
                    "Enter at least two dimensions."
            );
        }

        for (int x : p) {

            if (x <= 0) {
                throw new IllegalArgumentException(
                        "Dimensions must be positive."
                );
            }

        }

        int n = p.length - 1;

        long[][] dp = new long[n + 1][n + 1];

        int[][] split = new int[n + 1][n + 1];

        /*
         * Interval Dynamic Programming
         */

        for (int length = 2; length <= n; length++) {

            for (int i = 1;
                 i <= n - length + 1;
                 i++) {

                int j = i + length - 1;

                dp[i][j] = Long.MAX_VALUE / 4;

                for (int k = i; k < j; k++) {

                    long multiplicationCost =
                            (long) p[i - 1]
                            * p[k]
                            * p[j];

                    long totalCost =
                            dp[i][k]
                            + dp[k + 1][j]
                            + multiplicationCost;

                    if (totalCost < dp[i][j]) {

                        dp[i][j] = totalCost;

                        split[i][j] = k;

                    }
                }
            }
        }

        /*
         * Naive left-to-right multiplication
         */

        long naiveCost = 0;

        int rows = p[0];

        int cols = p[1];

        for (int i = 2; i <= n; i++) {

            naiveCost +=
                    (long) rows
                    * cols
                    * p[i];

            cols = p[i];
        }

        long optimizedCost = dp[1][n];

        long saved = naiveCost - optimizedCost;

        double efficiency =
                naiveCost == 0
                ? 0
                : saved * 100.0 / naiveCost;

        String parenthesization =
                buildParenthesization(
                        split,
                        1,
                        n
                );

        return new OptimizationResult(

                optimizedCost,

                naiveCost,

                saved,

                efficiency,

                parenthesization,

                dp,

                p.clone(),

                n

        );
    }

    private String buildParenthesization(
            int[][] split,
            int i,
            int j
    ) {

        if (i == j) {

            return "A" + i;

        }

        int k = split[i][j];

        return "("
                + buildParenthesization(
                        split,
                        i,
                        k
                )
                + " × "
                + buildParenthesization(
                        split,
                        k + 1,
                        j
                )
                + ")";

    }
}