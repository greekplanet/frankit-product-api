package com.frankit.product_api.domain.product;

import com.frankit.product_api.application.dto.request.ProductRequest;
import com.frankit.product_api.application.dto.response.ProductPageResponse;
import com.frankit.product_api.application.dto.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("노트북");
        product.setDescription("고성능 노트북");
        product.setPrice(1500000);
        product.setShippingFee(3000);
        product.setCreatedAt(LocalDateTime.now());

        productRequest = new ProductRequest();
        productRequest.setName("노트북");
        productRequest.setDescription("고성능 노트북");
        productRequest.setPrice(1500000);
        productRequest.setShippingFee(3000);
    }

    @Test
    void createProduct_success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.createProduct(productRequest);

        assertNotNull(response);
        assertEquals("노트북", response.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponse response = productService.updateProduct(1L, productRequest);

        assertNotNull(response);
        assertEquals("노트북", response.getName());
        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_notFound_throwsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                productService.updateProduct(1L, productRequest));

        assertEquals("상품을 찾을 수 없습니다: ID 1", exception.getMessage());
        verify(productRepository).findById(1L);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void getProducts_success() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(Collections.singletonList(product), pageable, 1);
        when(productRepository.findAll(pageable)).thenReturn(page);

        ProductPageResponse response = productService.getProducts(pageable);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(0, response.getPage().getNumber());
        assertEquals(10, response.getPage().getSize());
        assertEquals(1, response.getPage().getTotalElements());
        assertEquals(1, response.getPage().getTotalPages());
        verify(productRepository).findAll(pageable);
    }
}
