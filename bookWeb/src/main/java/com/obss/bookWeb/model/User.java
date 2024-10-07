package com.obss.bookWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.obss.bookWeb.constant.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name= "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email",unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @Column(name = "User_Role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "User_Reg_Time")
    private LocalDateTime registerTime;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private FavList favList;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private ReadList readList;





}
