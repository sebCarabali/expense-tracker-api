package com.example.expensetracker.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue(generator = "category_seq_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_seq_gen", sequenceName = "categories_id_seq", allocationSize = 1, initialValue = 1)
    private Short id;
    private String name;

    
    public Category(Short id) {
        this.id = id;
    }
}
