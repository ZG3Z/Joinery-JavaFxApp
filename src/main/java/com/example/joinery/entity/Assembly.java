package com.example.joinery.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "assembly")
@PrimaryKeyJoinColumn(name = "idA")
public class Assembly extends Service {
    @Basic
    private String productName;
    @Basic
    private int size;
    @ManyToMany()
    @JoinTable(
            name = "assemblymaterial",
            joinColumns = @JoinColumn(name = "idA"),
            inverseJoinColumns = @JoinColumn(name = "idM")
    )
    private List<Material> materialList = new ArrayList<>();

    public Assembly() {}

    public Assembly(long id, String productName, int size) {
        super();
        setId(id);
        setCostPerDay(100);

        setProductName(productName);
        setSize(size);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size >= 1 && size <= 10) {
            this.size = size;
        } else {
            throw new IllegalArgumentException("Invalid size value");
        }
    }

    public List<Material> getMaterials() {
        return materialList;
    }

    public void addMaterial(Material newMaterial){
        if(!materialList.contains(newMaterial)) {
            materialList.add(newMaterial);
            newMaterial.addAssembly(this);
        }
    }

    public void removeMaterial(Material material){
        if(materialList.contains(material)) {
            materialList.remove(material);
            material.removeAssembly(this);
        }
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    @Transient
    @Override
    public int getDaysToComplete() {
        return size + materialList.size();
    }

    @Transient
    @Override
    public int getTotalServiceCost() {
        return getCostPerDay() * getDaysToComplete() + materialList.stream().mapToInt(m -> m.getPrice()).sum();
    }
}
