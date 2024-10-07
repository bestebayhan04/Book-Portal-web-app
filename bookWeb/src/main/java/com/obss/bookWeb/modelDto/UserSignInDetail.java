package com.obss.bookWeb.modelDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignInDetail {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer favlistId;
    private Integer readlistId;

}
