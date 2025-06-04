package com.frankit.product_api.domain.option;

import com.frankit.product_api.application.dto.response.OptionResponse;
import com.frankit.product_api.domain.product.Product;
import com.frankit.product_api.domain.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.frankit.productapi.application.dto.request.OptionRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {
    @Mock
    private OptionRepository optionRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OptionService optionService;

    private Product product;
    private Option option;
    private OptionRequest optionRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);

        option = new Option();
        option.setId(1L);
        option.setName("색상");
        option.setType(OptionType.SELECT);
        option.setValues(List.of("빨강", "파랑"));
        option.setAdditionalPrice(1000);
        option.setProduct(product);

        optionRequest = new OptionRequest();
        optionRequest.setName("색상");
        optionRequest.setType("SELECT");
        optionRequest.setValues(List.of("빨강", "파랑"));
        optionRequest.setAdditionalPrice(1000);
    }

    @Test
    void createOption_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.save(any(Option.class))).thenReturn(option);

        OptionResponse response = optionService.createOption(1L, optionRequest);

        assertNotNull(response);
        assertEquals("색상", response.getName());
        verify(productRepository).findById(1L);
        verify(optionRepository).save(any(Option.class));
    }

    @Test
    void createOption_productNotFound_throwsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                optionService.createOption(1L, optionRequest));

        assertEquals("상품을 찾을 수 없습니다: ID 1", exception.getMessage());
        verify(productRepository).findById(1L);
        verifyNoInteractions(optionRepository);
    }

    @Test
    void getOptions_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(optionRepository.findByProductId(1L)).thenReturn(Collections.singletonList(option));

        List<OptionResponse> response = optionService.getOptions(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("색상", response.get(0).getName());
        verify(productRepository).findById(1L);
        verify(optionRepository).findByProductId(1L);
    }
}
