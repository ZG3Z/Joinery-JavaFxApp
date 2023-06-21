/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "woodLikeMaterial")
@PrimaryKeyJoinColumn(name = "idWL")
public class WoodLikeMaterial extends Material{
    @Basic
    private String material;
    @Basic
    private String manufacturer;

    public WoodLikeMaterial(){}

    public WoodLikeMaterial(long id, String material, String manufacturer, int price){
        super();
        setId(id);
        setPrice(price);

        setMaterial(material);
        setManufacturer(manufacturer);
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
}
