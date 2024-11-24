package com.balance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private SubcategoryType type;

    @OneToMany(mappedBy = "subcategory")
    private List<Transaction> transactions;

    public enum SubcategoryType {
        RESTAURANTS, MARKETS, SALARY, ENTERTAINMENT, UTILITIES // etc.
    }
}
