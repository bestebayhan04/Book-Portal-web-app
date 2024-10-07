package com.obss.bookWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "ReadListItems")
public class ReadListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "readlistitem_id")
    private Integer readlistitemId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "readlist_id")
    private ReadList readList;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
