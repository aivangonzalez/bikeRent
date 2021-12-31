
package com.app.bikeRent.controllers;

import com.app.bikeRent.exceptions.ExceptionService;
import com.app.bikeRent.services.BikeService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bicicleta")
public class BikeController {
    
    @Autowired
    private BikeService bService;
    
    @GetMapping("/registro")
    public String form(){
        return "form-bike";
    }
    
    @PostMapping("/registro")
    public String save(ModelMap model, @RequestParam String brand, @RequestParam String modelo, @RequestParam String tyre, @RequestParam String type, Integer hour_price, Integer total_quantity){
        try {
            bService.create(brand, modelo, tyre, type, hour_price, total_quantity);
            model.put("exito", "La bici fue registrada exitosamente!!");
            return "form-bike";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "form-bike";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String formModify(ModelMap model, @PathVariable String id){
        try {
            model.put("bicicleta", bService.searchById(id));
            return "modify-bike";
        } catch (ExceptionService e) {
            return "modify-bike";
        }
    }
    
    @PostMapping("/modificar/{id}")
    public String modify(ModelMap model, @PathVariable String id, @RequestParam String brand, @RequestParam String modelo, @RequestParam String tyre, @RequestParam String type, Integer hour_price, Integer total_quantity){
        try {
            bService.modify(id, brand, modelo, tyre, type, hour_price, total_quantity);
            model.put("exito", "La bici fue modificada exitosamente!!");
            model.put("bicicleta", bService.searchById(id));
            return "modify-bike";
        } catch (ExceptionService e) {
            try {
                model.put("error", e.getMessage());
                model.put("bicicleta", bService.searchById(id));
                return "modify-bike";
            } catch (ExceptionService ex) {
                return "redirect:/bicicleta/lista";
            }
        }
    }
    
    @GetMapping("/lista")
    public String list(ModelMap model) {
        try {
            model.addAttribute("bicicletas", bService.searchAll());
            return "list-bike";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "list-bike";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String drop(@PathVariable String id) {
        try {
            bService.drop(id);
            return "redirect:/bicicleta/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String high(@PathVariable String id) {
        try {
            bService.high(id);
            return "redirect:/bicicleta/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
}
