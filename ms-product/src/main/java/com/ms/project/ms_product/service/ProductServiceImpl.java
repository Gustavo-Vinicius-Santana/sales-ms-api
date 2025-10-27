package com.ms.project.ms_product.service;

import com.ms.project.ms_product.model.Product;
import com.ms.project.ms_product.dto.ProductRequestDTO;
import com.ms.project.ms_product.dto.ProductResponseDTO;
import com.ms.project.ms_product.repository.ProductRepository;
import com.ms.project.ms_product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private ProductResponseDTO convertToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getNome(),
                product.getQuantidade(),
                product.getDescricao(),
                product.getPreco()
        );
    }

    private Product convertToEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setNome(productRequestDTO.getNome());
        product.setQuantidade(productRequestDTO.getQuantidade());
        product.setDescricao(productRequestDTO.getDescricao());
        product.setPreco(productRequestDTO.getPreco());
        return product;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = convertToEntity(productRequestDTO);
        Product savedProduct = productRepository.save(product);
        return convertToResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return convertToResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existingProduct.setNome(productRequestDTO.getNome());
        existingProduct.setQuantidade(productRequestDTO.getQuantidade());
        existingProduct.setDescricao(productRequestDTO.getDescricao());
        existingProduct.setPreco(productRequestDTO.getPreco());

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}