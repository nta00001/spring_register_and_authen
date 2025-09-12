package com.map_properties.spring_server.service;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.ProductDTO;
import com.map_properties.spring_server.request.FilterProductRequest; // Bạn sẽ cần tạo file này

public interface ProductService {
    ListResponseDTO<ProductDTO> getProducts(FilterProductRequest request);
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}