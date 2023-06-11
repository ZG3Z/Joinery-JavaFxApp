package com.example.joinery.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "conservation")
@PrimaryKeyJoinColumn(name = "idC")
public class Conservation extends Service {
    enum LevelOfDamage{high, low}
    private String levelOfDamage;

    @ManyToMany()
    @JoinTable(
            name = "conservationchemical",
            joinColumns = @JoinColumn(name = "idC"),
            inverseJoinColumns = @JoinColumn(name = "idCC")
    )
    private List<Chemical> chemicalList = new ArrayList<>();


    public Conservation(){}


    public Conservation(long id, String levelOfDamage, int dayToComplete, int costPerDay) {
        super();
        setId(id);
        setDaysToComplete(dayToComplete);
        setCostPerDay(costPerDay);
      //  setChemicalList(chemicalList);
     //   setWorkOrders(workOrders);

        this.levelOfDamage = levelOfDamage;
    }

    public String getLevelOfDamage() {
        return levelOfDamage;
    }

    public void setLevelOfDamage(String levelOfDamage) {
        if (Objects.equals(levelOfDamage, LevelOfDamage.high.name()) || Objects.equals(levelOfDamage, LevelOfDamage.low.name())) {
            this.levelOfDamage = levelOfDamage;
        } else {
            throw new IllegalArgumentException("Invalid level of damage value");
        }
    }

    public List<Long> getChemicalId() {
        return chemicalList.stream().map(Chemical::getId).collect(Collectors.toList());
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
        if(!chemicalList.contains(chemical)) {
            chemicalList.remove(chemical);
        }
    }

    @Override
    public int calculateServicePrice() {
        System.out.println(chemicalList.stream().mapToInt(c -> c.getPrice()).toArray());
        System.out.println(chemicalList.size());
        getChemicalList();
        return getCostPerDay() * getDaysToComplete() + chemicalList.stream().mapToInt(c -> c.getPrice()).sum();
    }

    @Override
    public String toString() {
        return "Conservation";
    }
}
