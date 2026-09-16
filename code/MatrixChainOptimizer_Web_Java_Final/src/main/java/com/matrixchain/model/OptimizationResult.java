package com.matrixchain.model;

public record OptimizationResult(

        long optimizedCost,

        long naiveCost,

        long saved,

        double efficiency,

        String parenthesization,

        long[][] dp,

        int[] dimensions,

        int matrixCount

) {
}