package com.microservices.product.service;

import com.microservices.product.model.Product;
import com.microservices.product.dto.ProductRequest;
import com.microservices.product.dto.ProductResponse;
import com.microservices.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = modelMapper.map(productRequest, Product.class);

        Product saved = productRepository.save(product);

        return modelMapper.map(saved, ProductResponse.class);
    }

    public Optional<ProductResponse> updateProduct(
            Long id,
            ProductRequest productRequest
    ) {

        return productRepository.findById(id)
                .map(existingProduct -> {
                    modelMapper.map(productRequest, existingProduct);
                    Product saved = productRepository.save(existingProduct);
                    return modelMapper.map(saved, ProductResponse.class);
                });
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = productRepository.findByActiveTrue();

        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class)).toList();
    }

    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return modelMapper.map(product, ProductResponse.class);
    }

    public boolean deleteProduct(Long id) {

        return productRepository.findById(id).map(p -> {
            p.setActive(false);
            productRepository.save(p);
            return true;}).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream().map(product -> modelMapper.map(product,ProductResponse.class)).toList();
    }
}