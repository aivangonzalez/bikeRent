package com.app.bikeRent.controllers;

import com.app.bikeRent.entities.Bike;
import com.app.bikeRent.entities.Client;
import com.app.bikeRent.exceptions.ExceptionService;
import com.app.bikeRent.services.BikeService;
import com.app.bikeRent.services.ClientService;
import com.app.bikeRent.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alquiler")
public class RentalController {

    @Autowired
    private RentalService rService;

    @Autowired
    private ClientService cService;

    @Autowired
    private BikeService bService;

    @GetMapping("/registro")
    public String form(ModelMap model) {
        try {
            model.put("clientes", cService.searchAllAvailable());
            model.put("bicicletas", bService.searchAllAvailable());
            return "form-rental";
        } catch (ExceptionService e) {
            return "/";
        }
    }

    @PostMapping("/registro")
    public String save(ModelMap model, Integer hours, @RequestParam(required = false) Client client, @RequestParam(required = false) Bike bike) {
        try {
            rService.create(hours, client, bike);
            bService.changeQuantitiesPlus(bike);
            model.put("clientes", cService.searchAllAvailable());
            model.put("bicicletas", bService.searchAllAvailable());
            model.put("exito", "El alquiler fue registrado exitosamente!!");
            return "form-rental";
        } catch (ExceptionService e) {
            try {
                model.put("error", e.getMessage());
                model.put("clientes", cService.searchAllAvailable());
                model.put("bicicletas", bService.searchAllAvailable());
                return "form-rental";
            } catch (Exception ex) {
                System.out.println(ex.getStackTrace());
                return "form-rental";
            }
        }
    }

    @GetMapping("/modificar/{id}")
    public String formModify(ModelMap model, @PathVariable String id) {
        try {
            model.put("alquiler", rService.searchById(id));
            model.put("clientes", cService.searchAllAvailable());
            model.put("bicicletas", bService.searchAllAvailable());
            return "modify-rental";
        } catch (ExceptionService e) {
            return "modify-rental";
        }
    }

    @PostMapping("/modificar/{id}")
    public String modify(ModelMap model, @PathVariable String id, Integer hours, @RequestParam(required = false) Client client, @RequestParam(required = false) Bike bike) {
        try {
            rService.modify(id, hours, client, bike);
            model.put("exito", "El alquiler fue modificado exitosamente!!");
            model.put("alquiler", rService.searchById(id));
            model.put("clientes", cService.searchAllAvailable());
            model.put("bicicletas", bService.searchAllAvailable());
            return "modify-rental";
        } catch (ExceptionService e) {
            try {
                model.put("error", e.getMessage());
                model.put("alquiler", rService.searchById(id));
                model.put("clientes", cService.searchAllAvailable());
                model.put("bicicletas", bService.searchAllAvailable());
                return "modify-rental";
            } catch (ExceptionService ex) {
                return "redirect:/alquiler/lista";
            }
        }
    }

    @GetMapping("/lista")
    public String list(ModelMap model) {
        try {
            model.addAttribute("alquileres", rService.searchAll());
            return "list-rental";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "list-rental";
        }
    }

    @GetMapping("/baja/{id}")
    public String drop(@PathVariable String id) {
        try {
            rService.drop(id);
            bService.changeQuantitiesLess(rService.searchById(id).getBike());
            return "redirect:/alquiler/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String high(@PathVariable String id) {
        try {
            rService.high(id);
            bService.changeQuantitiesPlus(rService.searchById(id).getBike());
            return "redirect:/alquiler/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

}
