package com.obss.bookWeb.modelDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDto {

    @NotNull(message = "Email Id Is Mandatory ,can Not Be Null")
    @NotBlank(message = "Email Id Is Mandatory")
    @Column(name = "email", unique = true)
    private String email;

    // Remove password constraints for update operations
    private String password;

    @NotNull(message = "First Name can Not be Null")
    @NotBlank(message = "First Name can not be blank")
    private String firstName;

    @NotNull(message = "Last Name can Not be Null")
    @NotBlank(message = "Last Name can not be blank")
    private String lastName;

}
