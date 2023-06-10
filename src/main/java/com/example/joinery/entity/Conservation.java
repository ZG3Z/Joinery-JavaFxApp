package com.example.joinery.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "conservation")
@PrimaryKeyJoinColumn(name = "idC")
public class Conservation extends Service {
    private String levelOfDamage;

    @ManyToMany()
    @JoinTable(
            name = "conservationchemical",
            joinColumns = @JoinColumn(name = "idC"),
            inverseJoinColumns = @JoinColumn(name = "idCC")
    )
    private List<Chemical> chemicalList = new ArrayList<>();


    public Conservation(){}

    public Conservation(long id, String levelOfDamage, int dayToComplete, int costPerDay, List<Chemical> chemicalList, List<WorkOrder> workOrders) {
        super();
        setId(id);
        setDaysToComplete(dayToComplete);
        setCostPerDay(costPerDay);
        setChemicalList(chemicalList);
        setWorkOrders(workOrders);

        this.levelOfDamage = levelOfDamage;
    }

    public String getLevelOfDamage() {
        return levelOfDamage;
    }

    public void setLevelOfDamage(String levelOfDamage) {
        this.levelOfDamage = levelOfDamage;
    }

    public List<Long> getChemicalId() {
        return chemicalList.stream().map(Chemical::getId).collect(Collectors.toList());
    }

    public List<Chemical> getChemicalList() {
        return chemicalList;
    }

    public void setChemicalList(List<Chemical> chemicalList) {
        this.chemicalList = chemicalList;
    }

    @Override
    public String toString() {
        return "Conservation: " + " level of damage: " + getLevelOfDamage() + super.toString();
    }
}
