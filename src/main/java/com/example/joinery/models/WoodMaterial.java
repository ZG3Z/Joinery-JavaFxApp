/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "woodMaterial")
@PrimaryKeyJoinColumn(name = "idW")
public class WoodMaterial extends Material{
    @Basic
    private String woodType;
    @Basic
    private int hardness;

    public WoodMaterial(){}

    public String getWoodType() {
        return woodType;
    }

    public void setWoodType(String woodType) {
        this.woodType = woodType;
    }

    public int getHardness() {
        return hardness;
    }

    /**
     * Sets the hardness level of the object.
     *
     * @param hardness The hardness level to be set. Must be between 1 and 6 (inclusive).
     * @throws IllegalArgumentException if the provided hardness value is outside the valid range.
     */
    public void setHardness(int hardness) {
        if (hardness >= 1 && hardness <=6) {
            this.hardness = hardness;
        } else {
            throw new IllegalArgumentException("Invalid hardness value");
        }
    }
}
