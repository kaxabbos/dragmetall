package com.dragmetall.model;

import com.dragmetall.model.enums.Category;
import com.dragmetall.model.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Equipment {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String serial;
    private String inv;
    private int quantity;
    private double weight;
    private String date;
    private String description;
    private String img;
    private String provider;
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apps> apps;

    public Equipment(String name, Category category, String serial, int quantity, double weight, String inv, String date, String description, String provider) {
        this.name = name;
        this.category = category;
        this.serial = serial;
        this.quantity = quantity;
        this.weight = weight;
        this.inv = inv;
        this.date = date;
        this.description = description;
        this.provider = provider;
        status = Status.ACTIVE;
        this.apps = new ArrayList<>();
    }

    public void addApp(Apps apps) {
        this.apps.add(apps);
        apps.setEquipment(this);
    }
}
