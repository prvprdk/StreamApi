package org.example;

import java.util.HashSet;
import java.util.Set;


public class Department {
    private Integer id;
    private Integer parent;
    private String name;
    private Set<Department> child = new HashSet<>();

    public Department(Integer id, Integer parent, String name) {
        this.id = id;
        this.parent = parent;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", child=" + child +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Department> getChild() {
        return child;
    }

    public void setChild(Set<Department> child) {
        this.child = child;
    }
}
