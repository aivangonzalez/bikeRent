package com.app.bikeRent.services;

import com.app.bikeRent.entities.Bike;
import com.app.bikeRent.entities.Client;
import com.app.bikeRent.entities.Rental;
import com.app.bikeRent.exceptions.ExceptionService;
import com.app.bikeRent.repositories.BikeRepository;
import com.app.bikeRent.repositories.ClientRepository;
import com.app.bikeRent.repositories.RentalRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rRepo;

    @Autowired
    private ClientRepository cRepo;

    @Autowired
    private BikeRepository bRepo;

    
    @Transactional
    public void create(Integer hours, Client client, Bike bike) throws ExceptionService {
        validate(hours, client, bike);
        
        Rental r = new Rental();
        r.setCode(generatorCode());
        r.setRetal_date(new Date());
        r.setHours(hours);
        r.setFinal_price(hours * (bike.getHour_price()));
        r.setClient(client);
        r.setBike(bike);
        
        rRepo.save(r);
    }

    @Transactional
    public void modify(String id, Integer hours, Client client, Bike bike) throws ExceptionService {
        validate(hours, client, bike);
        validateId(id);
        
        Rental r = rRepo.findById(id).get();
        r.setHours(hours);
        r.setFinal_price(hours * (bike.getHour_price()));
        r.setClient(client);
        r.setBike(bike);
        
        rRepo.save(r);
    }
    
    @Transactional
    public void drop(String id) throws ExceptionService {
        validateId(id);
        
        Rental r = rRepo.findById(id).get();
        r.setAvailability(false);
        r.setReturn_date(new Date());
        
        rRepo.save(r);
    }
    
    @Transactional
    public void high(String id) throws ExceptionService {
        validateId(id);
        
        Rental r = rRepo.findById(id).get();
        r.setAvailability(true);
        r.setReturn_date(null);
        
        rRepo.save(r);
    }
    
    public List<Rental> searchAll() throws ExceptionService {
        return rRepo.findAll();
    }
    
    public Rental searchById(String id) throws ExceptionService {
        validateId(id);
        return rRepo.findById(id).get();
    }
    
    //Genera, segun la cantidad de alquileres, un codigo continuo al ultimo
    public Integer generatorCode() throws ExceptionService {
        Integer cant = rRepo.findAll().size();
        return cant + 1;
    }

    
    
    //----------------------------VALIDACIONES---------------------------
    public void validate(Integer hours, Client client, Bike bike) throws ExceptionService {
        
        //-------Valido la cantidad de horas
        if (hours == null || hours <= 0) {
            throw new ExceptionService("Error al ingresar las horas de alquiler.");
        }
        if (hours > 168) {
            throw new ExceptionService("La cantidad maxima de horas es 168.");
        }

        //-------Valido que haya un cliente 
        if (client == null) {
            throw new ExceptionService("Debe seleccionar un cliente antes de enviar el formulario!!");
        }
        Optional<Client> answerClient = cRepo.findById(client.getId());
        if (!answerClient.isPresent()) {
            throw new ExceptionService("No se encontró el cliente en la base de datos. Antes de crear un alquiler o modificarlo debe ingresar el cliente.");
        }
        
        
        //-------Valido que haya una bicicleta 
        if (bike == null) {
            throw new ExceptionService("Debe seleccionar una bicicleta antes de enviar el formulario!!");
        }
        Optional<Bike> answerBike = bRepo.findById(bike.getId());
        if (!answerBike.isPresent()) {
            throw new ExceptionService("No se encontró la bicicleta en la base de datos. Antes de crear un alquiler o modificarlo debe ingresar la bicicleta.");
        }
    }

    //Valido que haya alquiler con el id pasado por parametro
    public void validateId(String id) throws ExceptionService {
        Optional<Rental> answer = rRepo.findById(id);
        if (!answer.isPresent()) {
            throw new ExceptionService("No se encontró ningun alquiler.");
        }
    }

}
