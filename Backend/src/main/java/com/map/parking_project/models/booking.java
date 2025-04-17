package com.map.parking_project.models;

import jakarta.persistence.*;

@Table(name = "reservas")
@Entity
public class booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idBoking;

    public Long getIdBoking() {
        return idBoking;
    }

    public void setIdBoking(Long idBoking) {
        this.idBoking = idBoking;
    }

    public String getDayAndHour() {
        return dayAndHour;
    }

    public void setDayAndHour(String dayAndHour) {
        this.dayAndHour = dayAndHour;
    }

    public Integer getIDUser() {
        return IDUser;
    }

    public void setIDUser(Integer IDUser) {
        this.IDUser = IDUser;
    }

    @Column(nullable = false, length = 50)
    private String dayAndHour;

    @Column(nullable = false, length = 50)
    private Integer IDUser;


}
