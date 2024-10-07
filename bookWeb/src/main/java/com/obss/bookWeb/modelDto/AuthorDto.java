package com.obss.bookWeb.modelDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorDto {

    @NotNull(message = "Author name is mandatory and cannot be null")
    @NotBlank(message = "Author name is mandatory")
    private String name;
}

