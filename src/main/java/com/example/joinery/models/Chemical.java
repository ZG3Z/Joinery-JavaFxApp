/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chemical")
@Inheritance(strategy = InheritanceType.JOINED)
public class Chemical {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    @Basic
    private String name;
    @Basic
    private int toxicityLevel;
    @Basic
    private int price;

    @ManyToMany()
    @JoinTable(
            name = "conservationchemical",
            joinColumns = @JoinColumn(name = "idCC"),
            inverseJoinColumns = @JoinColumn(name = "idC")
    )
    private List<Conservation> conservationList = new ArrayList<>();

    public Chemical(){}

    public Chemical(long id, String name, int toxicityLevel, int price){
        setId(id);
        setName(name);
        setToxicityLevel(toxicityLevel);
        setPrice(price);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getToxicityLevel() {
        return toxicityLevel;
    }

    /**
     * Sets the level of toxicity for the service.
     *
     * @param toxicityLevel The level of toxicity to set. Must be within the range of 1 to 5 (inclusive).
     * @throws IllegalArgumentException If the provided toxicity level is outside the valid range.
     */
    public void setToxicityLevel(int toxicityLevel) {
        if (toxicityLevel >= 1 && toxicityLevel <= 5) {
            this.toxicityLevel = toxicityLevel;
        } else {
            throw new IllegalArgumentException("Invalid toxicity level value");
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Conservation> getConservationList() {
        return conservationList;
    }

    public void addConservation(Conservation newConservation){
        if(!conservationList.contains(newConservation)) {
            conservationList.add(newConservation);
        }
    }

    public void removeConservation(Conservation conservation){
        if(!conservationList.contains(conservation)) {
            conservationList.remove(conservation);
        }
    }

    public void setConservationList(List<Conservation> conservationList) {
        this.conservationList = conservationList;
    }
}
