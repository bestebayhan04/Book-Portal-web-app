package com.obss.bookWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
@Table(name= "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotNull(message = "Product book name is Mandatory ,can Not Be Null")
    @NotBlank(message = "Product book name is Mandatoryyyyy")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Product book image is Mandatory ,can Not Be Null")
    @NotBlank(message = "Product book image is Mandatory")
    @Column(name = "imageUrl")
    private String imageUrl;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Author author;


    @NotNull(message = "Product description is Mandatory ,can Not Be Null")
    @NotBlank(message = "Product description is Mandatory")
    @Size(min = 5, max = 50)
    @Column(name = "description")
    private String description;

    @NotNull(message = "Product category name is Mandatory ,can Not Be Null")
    @NotBlank(message = "Product category name is Mandatory")
    @Column(name = "category_name")
    private String category;

}
