package com.frankit.product_api.domain.option;

import com.frankit.product_api.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OptionType type;

    @ElementCollection
    @CollectionTable(name = "option_values", joinColumns = @JoinColumn(name = "option_id"))
    @Column(name = "option_values")
    private List<String> values = new ArrayList<>();

    @Column(nullable = false)
    private double additionalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}

enum OptionType {
    INPUT, SELECT
}