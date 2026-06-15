package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.license.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto getById(Long id);

    ProductDto create(ProductDto dto);

    ProductDto update(Long id, ProductDto dto);
}