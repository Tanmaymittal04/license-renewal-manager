package com.yourorg.licenserenewalmanager.license.service;

import com.yourorg.licenserenewalmanager.common.exception.NotFoundException;
import com.yourorg.licenserenewalmanager.license.entity.LicenseProduct;
import com.yourorg.licenserenewalmanager.license.repository.LicenseProductRepository;
import com.yourorg.licenserenewalmanager.license.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final LicenseProductRepository productRepository;

    public ProductServiceImpl(LicenseProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        LicenseProduct product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return toDto(product);
    }

    @Override
    public ProductDto create(ProductDto dto) {
        LicenseProduct entity = new LicenseProduct();
        entity.setName(dto.getName());
        entity.setVendorName(dto.getVendorName());
        entity.setCategory(dto.getCategory());
        entity.setDefaultValidityMonths(dto.getDefaultValidityMonths());
        entity.setDescription(dto.getDescription());

        LicenseProduct saved = productRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        LicenseProduct entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        entity.setName(dto.getName());
        entity.setVendorName(dto.getVendorName());
        entity.setCategory(dto.getCategory());
        entity.setDefaultValidityMonths(dto.getDefaultValidityMonths());
        entity.setDescription(dto.getDescription());

        // JPA dirty checking will persist changes on transaction commit
        return toDto(entity);
    }

    private ProductDto toDto(LicenseProduct entity) {
        return new ProductDto(
                entity.getId(),
                entity.getName(),
                entity.getVendorName(),
                entity.getCategory(),
                entity.getDefaultValidityMonths(),
                entity.getDescription()
        );
    }
}