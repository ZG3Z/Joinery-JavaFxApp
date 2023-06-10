package com.example.joinery.entity;

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
    private String name;
    private int toxicityLevel;
    private int price;

    @ManyToMany()
    @JoinTable(
            name = "conservationchemical",
            joinColumns = @JoinColumn(name = "idCC"),
            inverseJoinColumns = @JoinColumn(name = "idC")
    )
    private List<Conservation> conservationList = new ArrayList<>();

    public Chemical(){}

    public Chemical(long id, String name, int toxicityLevel, int price, List<Conservation> conservationList){
        this.id = id;
        this.name = name;
        this.toxicityLevel = toxicityLevel;
        this.price = price;
        setConservationList(conservationList);
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

    public void setToxicityLevel(int toxicityLevel) {
        this.toxicityLevel = toxicityLevel;
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

    public void setConservationList(List<Conservation> conservationList) {
        this.conservationList = conservationList;
    }
}
