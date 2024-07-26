package com.group28.orderingSystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Flavor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer menuId;

//    @ManyToOne
//    @JoinColumn(name = "mid")
//    private Menu menu;

    private String flavorName;

    public Flavor(Integer menuId, String flavorName) {
        this.menuId = menuId;
        this.flavorName = flavorName;
    }

    public Flavor() {

    }

//    private String value;


}
