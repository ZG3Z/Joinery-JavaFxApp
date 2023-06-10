package com.example.joinery.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "woodLikeMaterial")
@PrimaryKeyJoinColumn(name = "idWL")
public class WoodLikeMaterial extends Material{
    private String material;
    private String manufacturer;

    public WoodLikeMaterial(){}

    public WoodLikeMaterial(long id, String material, String manufacturer, int price){
        super();
        setId(id);
        setPrice(price);

        this.material = material;
        this.manufacturer = manufacturer;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "WoodLikeMaterial: " +
                " material: " + getMaterial()  +
                ", manufacturer: " + getManufacturer() +  super.toString();
    }
}
