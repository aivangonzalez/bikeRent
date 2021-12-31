
package com.app.bikeRent.services;

import com.app.bikeRent.entities.Bike;
import com.app.bikeRent.exceptions.ExceptionService;
import com.app.bikeRent.repositories.BikeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BikeService {
    
    @Autowired
    private BikeRepository bRepo;
    
    @Transactional
    public void create(String brand, String model, String tyre, String type, Integer hour_price, Integer total_quantity) throws ExceptionService{
        validate(brand, model, tyre, type, hour_price, total_quantity);
        
        Bike b = new Bike();
        b.setBrand(brand);
        b.setModel(model);
        b.setTyre(tyre);
        b.setType(type);
        b.setHour_price(hour_price);
        b.setTotal_quantity(total_quantity);
        b.setAvailable_quantity(total_quantity);
        
        bRepo.save(b);
    }
    
    @Transactional
    public void modify(String id, String brand, String model, String tyre, String type, Integer hour_price, Integer total_quantity) throws ExceptionService {
        validateId(id);
        validate(brand, model, tyre, type, hour_price, total_quantity);
        
        Bike b = bRepo.findById(id).get();
        b.setBrand(brand);
        b.setModel(model);
        b.setTyre(tyre);
        b.setType(type);
        b.setHour_price(hour_price);
        b.setTotal_quantity(total_quantity);
        b.setAvailable_quantity(total_quantity);
        
        bRepo.save(b);
    }
    
    @Transactional
    public void drop(String id) throws ExceptionService {
        validateId(id);
        
        Bike b = bRepo.findById(id).get();
        b.setAvailability(false);
        
        bRepo.save(b);
    }
    
    public void high(String id) throws ExceptionService {
        validateId(id);
        
        Bike b = bRepo.findById(id).get();
        b.setAvailability(true);
        
        bRepo.save(b);
    }
    
    public void changeQuantitiesPlus(Bike b) throws ExceptionService {
        validateId(b.getId());
        
        b.setAvailable_quantity(b.getAvailable_quantity() - 1);
        b.setRented_quantity(b.getRented_quantity() + 1);
        bRepo.save(b);
    }
    
    public void changeQuantitiesLess(Bike b) throws ExceptionService {
        validateId(b.getId());
        
        b.setAvailable_quantity(b.getAvailable_quantity() + 1);
        b.setRented_quantity(b.getRented_quantity() - 1);
        bRepo.save(b);
    }
    
    public List<Bike> searchAll() throws ExceptionService {
        return bRepo.findAll();
    }
    
    public List<Bike> searchAllAvailable() throws ExceptionService {
        List<Bike> bs = bRepo.findAll();
        List<Bike> available = new ArrayList();
        for (Bike b : bs) {
            if (b.getAvailability() && (b.getTotal_quantity() != b.getRented_quantity())) {
                available.add(b);
            }
        }
        
        return available;
    }
    
    public Bike searchById(String id) throws ExceptionService {
        validateId(id);
        return bRepo.findById(id).get();
    }
    
    public List<Bike> searchByModel(String model) throws ExceptionService {
        return bRepo.searchByModel(model);
    }
    
    //----------------------------VALIDACIONES---------------------------
    public void validate(String brand, String model, String tyre, String type, Integer hour_price, Integer total_quantity) throws ExceptionService {
        //-------Valido la marca
        if (brand.trim().isEmpty() || brand == null) {
            throw new ExceptionService("Error al ingresar la marca.");
        } 
        
        //-------Valido el modelo
        if (model.trim().isEmpty() || model == null) {
            throw new ExceptionService("Error al ingresar el modelo de la bicicleta.");
        } 

        //-------Valido el rodado
        if (tyre.trim().isEmpty() || tyre == null) {
            throw new ExceptionService("Error al ingresar el rodado.");
        }

        //-------Valido el tipo de bicicleta
        if (type.trim().isEmpty() || type == null) {
            throw new ExceptionService("Error al ingresar el tipo de bicicleta.");
        } 
        
        //-------Valido el precio de alquiler por hora
        if (hour_price == null || hour_price <= 0) {
            throw new ExceptionService("Error al ingresar el valor por hora de alquiler.");
        }
        
        //-------Valido la cantidad total de bicicletas
        if (total_quantity == null || total_quantity < 0) {
            throw new ExceptionService("Error al ingresar la cantidad total de bicicletas.");
        }
        
    }

    //Valido que haya una bicicleta con el id pasado por parametro
    public void validateId(String id) throws ExceptionService {
        Optional<Bike> answer = bRepo.findById(id);
        if (!answer.isPresent()) {
            throw new ExceptionService("No se encontró ninguna bicicleta.");
        }
    }
    
}
