package com.obss.bookWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name= "FavList")
public class FavList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favlist_id")
    private Integer favlistId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "favList", cascade = CascadeType.ALL)
    private List<FavListItem> favListItems=new ArrayList<>();

    Double totalAmount;

}
