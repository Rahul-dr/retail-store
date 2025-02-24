package com.project.retail_store.service;

import com.project.retail_store.converter.ProductMapper;
import com.project.retail_store.dtos.ProductDto;
import com.project.retail_store.entity.Product;
import com.project.retail_store.entity.ProductCategory;
import com.project.retail_store.entity.Seller;
import com.project.retail_store.repository.ProductCategoryRepository;
import com.project.retail_store.repository.ProductRepository;
import com.project.retail_store.repository.SellerRepository;
import com.project.retail_store.service.interfaces.ProductService;
import com.project.retail_store.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        ProductCategory category = productCategoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> CommonUtils.logAndGetException("Category not found with ID: " + productDto.getCategoryId()));

        Product product = ProductMapper.INSTANCE.toEntity(productDto);
        product.setCategory(category);
        product.setDateCreated(LocalDateTime.now());
        product.setUserCreated("Admin");
        product.setActive(true);
        
        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(ProductMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> CommonUtils.logAndGetException("Product not found with ID: " + productId));

        product.setName(Objects.nonNull(productDto.getName()) ? productDto.getName() : product.getName());
        product.setDescription(Objects.nonNull(productDto.getDescription()) ? productDto.getDescription() : product.getDescription());
        product.setPrice(Objects.nonNull(productDto.getPrice()) ? productDto.getPrice() : product.getPrice());
        product.setImageUrl(Objects.nonNull(productDto.getImageUrl()) ? productDto.getImageUrl() : product.getImageUrl());
        product.setStockQuantity(Objects.nonNull(productDto.getStockQuantity()) ? productDto.getStockQuantity() : product.getStockQuantity());

        // ✅ Update Category if provided
        if (Objects.nonNull(productDto.getCategoryId())) {
            ProductCategory category = productCategoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> CommonUtils.logAndGetException("Category not found with ID: " + productDto.getCategoryId()));
            product.setCategory(category);
        }

        // ✅ Update Seller if provided
        if (Objects.nonNull(productDto.getSellerId())) {
            Seller seller = sellerRepository.findById(productDto.getSellerId())
                    .orElseThrow(() -> CommonUtils.logAndGetException("Seller not found with ID: " + productDto.getSellerId()));
            product.setSeller(seller);
        }

        product.setDateModified(LocalDateTime.now());
        product.setUserModified("Admin");

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDto(updatedProduct);
    }


    @Override
    @Transactional
    public void disableProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> CommonUtils.logAndGetException("Product not found with ID: " + productId));

        product.setActive(false);
        product.setDateModified(LocalDateTime.now());
        product.setUserModified("Admin");

        productRepository.save(product);
    }
}
