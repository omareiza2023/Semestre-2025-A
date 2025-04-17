package com.map.parking_project.models;

import jakarta.persistence.*;

@Table(name = "servicios")
@Entity
public class mapServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long isService;

    public Long getIsService() {
        return isService;
    }

    public void setIsService(Long isService) {
        this.isService = isService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String price;

    @Column(nullable = false)
    private String description;

}
