package com.obss.bookWeb.modelDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    @NotNull(message = "Product name is Mandatory, can Not Be Null")
    @NotBlank(message = "Product name is Mandatory")
    private String name;

    @NotNull(message = "Product Image is Mandatory, can Not Be Null")
    @NotBlank(message = "Product Image is Mandatory")
    private String imageUrl;

    @NotNull(message = "Product description is Mandatory, can Not Be Null")
    @NotBlank(message = "Product description is Mandatory")
    @Size(min = 5, max = 50)
    private String description;

    @NotNull(message = "Product category name is Mandatory, can Not Be Null")
    @NotBlank(message = "Product category name is Mandatory")
    private String category;

    @NotNull(message = "Author name is Mandatory, can Not Be Null")
    @NotBlank(message = "Author name is Mandatory")
    private String authorname;
}
