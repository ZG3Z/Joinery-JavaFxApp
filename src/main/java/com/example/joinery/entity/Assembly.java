package com.example.joinery.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "assembly")
@PrimaryKeyJoinColumn(name = "idA")
public class Assembly extends Service {
    private String productName;

    @ManyToMany()
    @JoinTable(
            name = "assemblymaterial",
            joinColumns = @JoinColumn(name = "idA"),
            inverseJoinColumns = @JoinColumn(name = "idM")
    )
    private List<Material> materials = new ArrayList<>();

    public Assembly() {}

    public Assembly(long id, String productName, int dayToComplete, int costPerDay, List<Material> materials, List<WorkOrder> workOrders) {
        super();
        setId(id);
        setDaysToComplete(dayToComplete);
        setCostPerDay(costPerDay);
        setMaterials(materials);
        setWorkOrders(workOrders);

        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public List<Long> getMaterialsId() {
        return materials.stream().map(Material::getId).collect(Collectors.toList());
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }


    @Override
    public String toString() {
        return "Assembly: " + " product name: " + getProductName() + super.toString();
    }
}
