
package com.app.bikeRent.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Bike {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String brand;
    private String model;
    private String tyre;
    private String type;
    private Integer hour_price;
    private Integer total_quantity;
    private Integer rented_quantity;
    private Integer available_quantity;
    private Boolean availability;

    public Bike() {
        this.availability = true;
        this.rented_quantity = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    
    
    public String getTyre() {
        return tyre;
    }

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHour_price() {
        return hour_price;
    }

    public void setHour_price(Integer hour_price) {
        this.hour_price = hour_price;
    }

    public Integer getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(Integer total_quantity) {
        this.total_quantity = total_quantity;
    }

    public Integer getRented_quantity() {
        return rented_quantity;
    }

    public void setRented_quantity(Integer rented_quantity) {
        this.rented_quantity = rented_quantity;
    }

    public Integer getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        this.available_quantity = available_quantity;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Bike{" + "id=" + id + ", brand=" + brand + ", model=" + model + ", tyre=" + tyre + ", type=" + type + ", hour_price=" + hour_price + ", total_quantity=" + total_quantity + ", rented_quantity=" + rented_quantity + ", available_quantity=" + available_quantity + ", availability=" + availability + '}';
    }

    
}
