package com.dragmetall.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Department {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String name;
    private String address;
    private String description;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipment> equipments;

    public Department(String name, String address, String description) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.equipments = new ArrayList<>();
    }

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
        equipment.setDepartment(this);
    }

    public int countQuantity() {
        AtomicInteger i = new AtomicInteger();
        equipments.forEach(equipment -> i.addAndGet(equipment.getQuantity()));
        return i.get();
    }
}
