/**
 * @Author: Zuzanna Gez
 */

package com.example.joinery.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "material")
@Inheritance(strategy = InheritanceType.JOINED)
public class Material {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private long id;

    @Basic
    private int price;

    @ManyToMany()
    @JoinTable(
            name = "assemblymaterial",
            joinColumns = @JoinColumn(name = "idM"),
            inverseJoinColumns = @JoinColumn(name = "idA")
    )
    private List<Assembly> assemblyList = new ArrayList<>();

    public Material(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Assembly> getAssemblyList() {
        return assemblyList;
    }

    public void addAssembly(Assembly newAssembly){
        if(!assemblyList.contains(newAssembly)) {
            assemblyList.add(newAssembly);
        }
    }

    public void removeAssembly(Assembly assembly){
        if(assemblyList.contains(assembly)){
            assemblyList.remove(assembly);
        }
    }

    public void setAssemblyList(List<Assembly> assemblyList) {
        this.assemblyList = assemblyList;
    }
}
