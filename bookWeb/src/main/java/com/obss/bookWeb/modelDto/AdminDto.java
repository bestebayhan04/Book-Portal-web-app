package com.obss.bookWeb.modelDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDto {

    @NotNull(message = "Email Id Is Mandatory, can Not Be Null")
    @NotBlank(message = "Email Id Is Mandatory")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull(message = "Password is Mandatory, can not be null")
    @NotBlank(message = "Password Is Mandatory")
    @Size(max = 10,min = 5,message = "Password length should be more than 5 Character")
    private String password;

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be blank")
    private String firstName;

    @NotNull(message = "Last name can not be null")
    @NotBlank(message = "Last name can not be blank")
    private String lastName;


}
