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
    private List<Material> materialList = new ArrayList<>();

    public Assembly() {}

    public Assembly(long id, String productName, int dayToComplete, int costPerDay) {
        super();
        setId(id);
        setDaysToComplete(dayToComplete);
        setCostPerDay(costPerDay);

        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public List<Long> getMaterialsId() {
        return materialList.stream().map(Material::getId).collect(Collectors.toList());
    }

    public List<Material> getMaterials() {
        return materialList;
    }

    public void addMaterial(Material newMaterial){
        if(!materialList.contains(newMaterial)) {
            materialList.add(newMaterial);
        }
    }

    public void removeMaterial(Material material){
        if(!materialList.contains(material)) {
            materialList.remove(material);
        }
    }

    @Override
    public int calculateServicePrice() {
        return getCostPerDay() * getDaysToComplete() + materialList.stream().mapToInt(m -> m.getPrice()).sum();
    }

    @Override
    public String toString() {
        return "Assembly: " + " product name: " + getProductName() + super.toString();
    }
}
