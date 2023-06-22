/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * A constant representing the cost per day for assembly, set to 100.
     */
    public static int COST_PER_DAY_ASSEMBLY = 100;

    public Assembly() {}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSize() {
        return size;
    }

    /**
     * Sets the size of the object subject to conservation.
     * The size must be within the range of 1 to 10 (inclusive).
     *
     * @param size The size value to be set.
     * @throws IllegalArgumentException if the size value is invalid.
     */
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
        }
    }

    public void removeMaterial(Material material){
        if(materialList.contains(material)) {
            materialList.remove(material);
        }
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    @Transient
    @Override
    public int getCostPerDay() {
        return COST_PER_DAY_ASSEMBLY;
    }

    /**
     * Calculates the number of days required to complete the conservation service.
     * The number of days is determined by adding the size of the object subject to conservation
     * and the number of materials in the material list.
     *
     * @return The number of days to complete the service.
     */
    @Transient
    @Override
    public int getDaysToComplete() {
        return size + materialList.size();
    }

    /**
     * Calculates the total cost of the conservation service.
     * The total cost is calculated by multiplying the cost per day with the number of days to complete the service,
     * and adding the prices of all the materials in the material list.
     *
     * @return The total service cost.
     */
    @Transient
    @Override
    public int getTotalServiceCost() {
        return getCostPerDay() * getDaysToComplete() + materialList.stream().mapToInt(Material::getPrice).sum();
    }
}
