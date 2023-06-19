package com.example.joinery.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "woodMaterial")
@PrimaryKeyJoinColumn(name = "idW")
public class WoodMaterial extends Material{
    @Basic
    private String woodType;
    @Basic
    private int hardness;

    public WoodMaterial(){}

    public WoodMaterial(long id, String woodType, int hardness, int price){
        super();
        setId(id);
        setPrice(price);

        setWoodType(woodType);
        setHardness(hardness);
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
        if (hardness >= 1 && hardness <=6) {
            this.hardness = hardness;
        } else {
            throw new IllegalArgumentException("Invalid hardness value");
        }
    }
}
