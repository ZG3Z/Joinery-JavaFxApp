package com.example.joinery.entity;

import com.example.joinery.entity.License;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "specialization")
public class Specialization {
    enum CategorySpecialization{
        assembly, conservation
    }
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;
    private String name;
    private String category;

    @OneToMany(mappedBy = "idS")
    private List<License> licenses  = new ArrayList<>();

    public Specialization(){}

    public Specialization(long id, String name, String category, List<License> licenses){
        this.id = id;
        this.name = name;
        this.category = category;
        setLicenses(licenses);
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
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

    public List<License> getLicenses() {
        return licenses;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
