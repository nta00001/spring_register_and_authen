package com.map_properties.spring_server.mapper;

import org.springframework.stereotype.Component;

import com.map_properties.spring_server.dto.ProductDTO;
import com.map_properties.spring_server.entity.Product;
import java.util.UUID; // Thêm import này

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDTO.builder()
                .id(product.getId())
                .uuid(product.getUuid())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        // Thay vì dùng builder, chúng ta tạo một đối tượng Product mới.
        // Điều này sẽ kích hoạt việc tự động tạo UUID trong file Product.java
        Product product = new Product();

        // Cập nhật các trường từ DTO
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setImageUrl(productDTO.getImageUrl());

        // Chúng ta không cần set id, uuid, createdAt, updatedAt vì chúng sẽ được quản lý tự động.

        return product;
    }
}