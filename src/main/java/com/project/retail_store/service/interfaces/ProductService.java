package com.project.retail_store.service.interfaces;

import com.project.retail_store.dtos.ProductDto;
import java.util.List;

public interface ProductService {
  ProductDto addProduct(ProductDto productDto);

  List<ProductDto> getAllProducts();

  ProductDto updateProduct(Long productId, ProductDto productDto);

  void disableProduct(Long productId);
}
