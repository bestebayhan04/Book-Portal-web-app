package com.obss.bookWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "FavListItems")
public class FavListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favlistitem_id")
    private Integer favlistitemId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "favlist_id")
    private FavList favList;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
