package com.obss.bookWeb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name= "ReadList")
public class ReadList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "readlist_id")
    private Integer readlistId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "readList", cascade = CascadeType.ALL)
    private List<ReadListItem> readListItems=new ArrayList<>();

    Double totalAmount;
}
