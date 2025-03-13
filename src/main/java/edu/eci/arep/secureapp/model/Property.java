package edu.eci.arep.secureapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;
    private String address;
    private Double price;
    private String size;
    private String description;

    public Property(){}

    public Property(String address, Double price, String size, String description){
        this.address=address;
        this.price= price;
        this.size= size;
        this.description=description;
    }


    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    

    public Double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
