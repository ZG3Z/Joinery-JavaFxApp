package com.example.joinery.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialization")
public class Specialization {
    public enum CategorySpecialization{Assembly, Conservation}

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @Basic
    private String name;

    @Enumerated(value = EnumType.STRING)
    private CategorySpecialization category;

    @OneToMany(mappedBy = "idS", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<License> licenses  = new ArrayList<>();

    public Specialization(){}

    public Specialization(long id, String name, CategorySpecialization category){
        setId(id);
        setName(name);
        setCategory(category);
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

    public CategorySpecialization getCategory() {
        return category;
    }

    public void setCategory(CategorySpecialization category) {
        this.category = category;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public void addLicense(License newLicense){
        if(!licenses.contains(newLicense)) {
            licenses.add(newLicense);
        }
    }

    public void removeLicense(License license){
        if(licenses.contains(license)) {
            licenses.remove(license);
        }
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }
}
