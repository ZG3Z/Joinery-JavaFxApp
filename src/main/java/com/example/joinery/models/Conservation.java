/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import com.example.joinery.enums.LevelOfDamage;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conservation")
@PrimaryKeyJoinColumn(name = "idC")
public class Conservation extends Service {

    /**
     * Represents the level of damage to an object subjected to conservation.
     * It is an enumerated type {@link LevelOfDamage}.
     */
    @Enumerated(EnumType.STRING)
    private LevelOfDamage levelOfDamage;

    @ManyToMany()
    @JoinTable(
            name = "conservationchemical",
            joinColumns = @JoinColumn(name = "idC"),
            inverseJoinColumns = @JoinColumn(name = "idCC")
    )
    private List<Chemical> chemicalList = new ArrayList<>();

    /**
     * A constant representing the cost per day for conservation, set to 200.
     */
    public static int COST_PER_DAY_CONSERVATION = 200;

    public Conservation(){}

    public LevelOfDamage getLevelOfDamage() {
        return levelOfDamage;
    }

    public void setLevelOfDamage(LevelOfDamage levelOfDamage) {
        this.levelOfDamage = levelOfDamage;
    }

    public List<Chemical> getChemicalList() {
        return chemicalList;
    }

    public void addChemical(Chemical newChemical){
        if(!chemicalList.contains(newChemical)) {
            chemicalList.add(newChemical);
        }
    }

    public void removeChemical(Chemical chemical){
        if(chemicalList.contains(chemical)) {
            chemicalList.remove(chemical);
        }
    }

    public void setChemicalList(List<Chemical> chemicalList) {
        this.chemicalList = chemicalList;
    }

    @Transient
    @Override
    public int getCostPerDay() {
        return COST_PER_DAY_CONSERVATION;
    }

    /**
     * Calculates the number of days needed to complete the service.
     * The number of days to complete is determined
     * by the number of chemicals in the chemical list
     * and the level of damage (high - 10, low - 5).
     *
     * @return The number of days to complete the service.
     */
    @Transient
    @Override
    public int getDaysToComplete() {
        int sizeLevelOfDamage = LevelOfDamage.high.equals(levelOfDamage)? 10 : LevelOfDamage.low.equals(levelOfDamage)? 5: 0;

        return chemicalList.size() + sizeLevelOfDamage;
    }

    /**
     * Calculates the total cost of the service.
     * The total cost is calculated by multiplying the cost per day
     * with the number of days to complete the service,
     * and adding the prices of all the chemicals in the chemical list.
     *
     * @return The total service cost.
     */
    @Transient
    @Override
    public int getTotalServiceCost() {
        return getCostPerDay() * getDaysToComplete() + chemicalList.stream().mapToInt(Chemical::getPrice).sum();
    }
}
