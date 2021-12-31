package com.app.bikeRent.services;

import com.app.bikeRent.entities.Client;
import com.app.bikeRent.exceptions.ExceptionService;
import com.app.bikeRent.repositories.ClientRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository cRepo;

    @Transactional
    public void create(String name, String surname, Long dni, String phone) throws ExceptionService {
        validate(name, surname, dni, phone);
        validateSearchByDni(dni);

        Client c = new Client();
        c.setName(name);
        c.setSurname(surname);
        c.setDni(dni);
        c.setPhone(phone);

        cRepo.save(c);

    }

    @Transactional
    public void modify(String id, String name, String surname, Long dni, String phone) throws ExceptionService {
        validateId(id);
        validate(name, surname, dni, phone);

        Client c = cRepo.findById(id).get();
        c.setName(name);
        c.setSurname(surname);
        c.setDni(dni);
        c.setPhone(phone);

        cRepo.save(c);
    }

    @Transactional
    public void drop(String id) throws ExceptionService {
        validateId(id);

        Client c = cRepo.findById(id).get();
        c.setAvailability(false);

        cRepo.save(c);
    }

    @Transactional
    public void high(String id) throws ExceptionService {
        validateId(id);

        Client c = cRepo.findById(id).get();
        c.setAvailability(true);

        cRepo.save(c);
    }

    public List<Client> searchAll() throws ExceptionService {
        return cRepo.findAll();
    }

    public List<Client> searchAllAvailable() throws ExceptionService {
        List<Client> cs = cRepo.findAll();
        List<Client> available = new ArrayList();
        for (Client c : cs) {
            if (c.getAvailability()) {
                available.add(c);
            }
        }

        return available;
    }

    public Client searchById(String id) throws ExceptionService {
        validateId(id);
        return cRepo.findById(id).get();
    }

    public Client searchByDni(Long dni) throws ExceptionService {
        return cRepo.searchByDni(dni);
    }

    //----------------------------VALIDACIONES---------------------------
    public void validate(String name, String surname, Long dni, String phone) throws ExceptionService {
        //-------Valido el nombre ingresado
        if (name.trim().isEmpty() || name == null) {
            throw new ExceptionService("Error al ingresar el nombre.");
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic((name.charAt(i)))) {
                throw new ExceptionService("El nombre no debe contener numeros.");
            }
        }

        //-------Valido el apellido ingresado
        if (surname.trim().isEmpty() || surname == null) {
            throw new ExceptionService("Error al ingresar el apellido.");
        }
        for (int i = 0; i < surname.length(); i++) {
            if (!Character.isAlphabetic((surname.charAt(i)))) {
                throw new ExceptionService("El apellido no debe contener numeros.");
            }
        }

        //-------Valido el dni
        if (dni == null || dni < 10000000) {
            throw new ExceptionService("Error al ingresar el documento nacional. Debe contener 8 caracteres.");
        }

        //------Valido el numero de celular
        if (phone.trim().isEmpty() || phone == null || phone.length() < 10) {
            throw new ExceptionService("Error al ingresar el numero de celular. Debe contener 10 caracteres.");
        }
    }

    public void validateSearchByDni(Long dni) throws ExceptionService {
        Client c = cRepo.searchByDni(dni);
        if (c != null) {
            throw new ExceptionService("Ya se encuentra un cliente con el mismo dni.");
        }
    }

    //Valido que haya un cliente con el id pasado por parametro
    public void validateId(String id) throws ExceptionService {
        Optional<Client> answer = cRepo.findById(id);
        if (!answer.isPresent()) {
            throw new ExceptionService("No se encontró ningun cliente.");
        }
    }

    //Metodo para validar segun cualquier expresion regular
    public Boolean regExp(String var, String regx) {
        //Esto se encarga de compilar la expresion regular para obtener el patrón
        Pattern pattern = Pattern.compile(regx);
        //Se crea una instacia del matcher
        Matcher matcher = pattern.matcher(var);
        //Esto devuelve un boolean segun sea el resultado
        return matcher.matches();
    }
}
