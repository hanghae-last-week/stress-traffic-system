package com.project.stress_traffic_system.product.controller;

import com.project.stress_traffic_system.product.model.dto.ProductResponseDto;
import com.project.stress_traffic_system.product.model.dto.ProductSearchCondition;
import com.project.stress_traffic_system.security.UserDetailsImpl;
import com.project.stress_traffic_system.product.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "상품전체조회", notes = "10개씩 페이징")
    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("page") int page) {
        return productService.getProducts(userDetails.getMember(), page);
    }

    @ApiOperation(value = "상품 상세페이지")
    @GetMapping("/products/{productId}")
    public ProductResponseDto getSeats(@PathVariable Long productId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return productService.getProduct(userDetails.getMember(), productId);
    }

    @ApiOperation(value = "상품검색", notes = "이름, 가격 범위로 필터링")
    @GetMapping("/products/search")
    public Page<ProductResponseDto> searchProducts(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ProductSearchCondition condition,
            @RequestParam("page") int page) {
        return productService.searchProducts(userDetails.getMember(), condition, page);
    }
}