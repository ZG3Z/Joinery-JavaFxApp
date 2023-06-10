package com.example.joinery.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "woodMaterial")
@PrimaryKeyJoinColumn(name = "idW")
public class WoodMaterial extends Material{
    private String woodType;
    private int hardness;

    public WoodMaterial(){}

    public WoodMaterial(long id, String woodType, int hardness, int price, List<Assembly> assemblyList){
        super();
        setId(id);
        setPrice(price);
        setAssemblyList(assemblyList);

        this.woodType = woodType;
        this.hardness = hardness;
    }

    public String getWoodType() {
        return woodType;
    }

    public void setWoodType(String woodType) {
        this.woodType = woodType;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) {
        this.hardness = hardness;
    }

    @Override
    public String toString() {
        return "WoodMaterial: " +
                " woodType: " + getWoodType()  +
                ", hardness: " + getHardness() +  super.toString();
    }
}
