package com.example.joinery.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conservation")
@PrimaryKeyJoinColumn(name = "idC")
public class Conservation extends Service {
    public enum LevelOfDamage {high, low}

    @Enumerated(EnumType.STRING)
    private LevelOfDamage levelOfDamage;

    @ManyToMany()
    @JoinTable(
            name = "conservationchemical",
            joinColumns = @JoinColumn(name = "idC"),
            inverseJoinColumns = @JoinColumn(name = "idCC")
    )
    private List<Chemical> chemicalList = new ArrayList<>();

    public Conservation(){}

    public Conservation(long id, LevelOfDamage levelOfDamage) {
        super();
        setId(id);
        setCostPerDay(200);

        setLevelOfDamage(levelOfDamage);
    }

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
            newChemical.addConservation(this);
        }
    }

    public void removeChemical(Chemical chemical){
        if(chemicalList.contains(chemical)) {
            chemicalList.remove(chemical);
            chemical.removeConservation(this);
        }
    }

    public void setChemicalList(List<Chemical> chemicalList) {
        this.chemicalList = chemicalList;
    }

    @Transient
    @Override
    public int getDaysToComplete() {
        int sizeLevelOfDamage = LevelOfDamage.high.equals(levelOfDamage)? 10 : LevelOfDamage.low.equals(levelOfDamage)? 5: 0;

        return chemicalList.size() + sizeLevelOfDamage;
    }

    @Transient
    @Override
    public int getTotalServiceCost() {
        return getCostPerDay() * getDaysToComplete() + chemicalList.stream().mapToInt(Chemical::getPrice).sum();
    }
}
