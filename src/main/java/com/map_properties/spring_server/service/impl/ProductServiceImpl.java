package com.map_properties.spring_server.service.impl;

import com.map_properties.spring_server.dto.ListResponseDTO;
import com.map_properties.spring_server.dto.ProductDTO;
import com.map_properties.spring_server.entity.Product;
import com.map_properties.spring_server.exception.ResourceNotFoundException;
import com.map_properties.spring_server.mapper.ListResponseMapper;
import com.map_properties.spring_server.mapper.ProductMapper;
import com.map_properties.spring_server.repository.ProductRepository;
import com.map_properties.spring_server.request.FilterProductRequest;
import com.map_properties.spring_server.service.ProductService;
import com.map_properties.spring_server.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ListResponseMapper<ProductDTO> listResponseMapper;

    @Override
    public ListResponseDTO<ProductDTO> getProducts(FilterProductRequest request) {
        FilterProductRequest filter = (request != null) ? request : new FilterProductRequest();

        Specification<Product> spec = Specification.where(ProductSpecification.filterByName(filter.getName()))
                .and(ProductSpecification.filterByPriceRange(filter.getMinPrice(), filter.getMaxPrice()));

        Page<ProductDTO> pageProducts = productRepository.findAll(spec, filter.toPageable())
                .map(productMapper::toDTO);

        return listResponseMapper.toDTO(pageProducts);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));
        return productMapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));

        // Cập nhật các trường của existingProduct từ productDTO
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setImageUrl(productDTO.getImageUrl());

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }
        productRepository.deleteById(id);
    }
}