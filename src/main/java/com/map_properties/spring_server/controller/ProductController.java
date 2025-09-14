package com.map_properties.spring_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.map_properties.spring_server.config.role.RequireRoles;
import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.ProductDTO;
import com.map_properties.spring_server.enums.ERole;
import com.map_properties.spring_server.request.FilterProductRequest;
import com.map_properties.spring_server.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<ListResponseDTO<ProductDTO>> getProducts(FilterProductRequest request) {
        return ResponseEntity.ok(productService.getProducts(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("")
    @RequireRoles({ ERole.ROLE_ADMIN })
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PutMapping("/{id}")
    @RequireRoles({ ERole.ROLE_ADMIN })
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    @RequireRoles({ ERole.ROLE_ADMIN })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}