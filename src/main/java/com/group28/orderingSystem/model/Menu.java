package com.group28.orderingSystem.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    private String taste;

    private Double smallPrice;
    private Double mediumPrice;
    private Double largePrice;
    private String descr;

    private String image;
    private Integer isLoaded;
    private Integer stock;

}
