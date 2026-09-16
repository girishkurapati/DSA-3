package com.matrixchain.controller;

import com.matrixchain.model.OptimizationRequest;
import com.matrixchain.model.OptimizationResult;
import com.matrixchain.service.MatrixChainService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MatrixChainController {

    private final MatrixChainService service;

    public MatrixChainController(
            MatrixChainService service
    ) {

        this.service = service;

    }

    @PostMapping("/optimize")
    public OptimizationResult optimize(
            @RequestBody OptimizationRequest request
    ) {

        return service.optimize(
                request.dimensions()
        );

    }
}