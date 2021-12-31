package com.app.bikeRent.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Rental {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Integer code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date retal_date;
    @Temporal(TemporalType.TIMESTAMP)
    private Date return_date;
    private Integer hours;
    private Integer final_price;
    private Boolean availability;

    @OneToOne
    private Client client;

    @OneToOne
    private Bike bike;

    public Rental() {
        this.availability = true;
        this.return_date = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Date getRetal_date() {
        return retal_date;
    }

    public void setRetal_date(Date retal_date) {
        this.retal_date = retal_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }
    

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getFinal_price() {
        return final_price;
    }

    public void setFinal_price(Integer final_price) {
        this.final_price = final_price;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    @Override
    public String toString() {
        return "Rental{" + "id=" + id + ", code=" + code + ", retal_date=" + retal_date + ", return_date=" + return_date + ", hours=" + hours + ", final_price=" + final_price + ", availability=" + availability + ", client=" + client + ", bike=" + bike + '}';
    }

} 
