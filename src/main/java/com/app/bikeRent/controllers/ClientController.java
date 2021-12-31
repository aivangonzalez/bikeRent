
package com.app.bikeRent.controllers;

import com.app.bikeRent.exceptions.ExceptionService;
import com.app.bikeRent.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente")
public class ClientController {
    
    @Autowired
    private ClientService cService;
    
    @GetMapping("/registro")
    public String form(){
        return "form-client";
    }
    
    @PostMapping("/registro")
    public String save(ModelMap model, @RequestParam String name, @RequestParam String surname, Long dni, @RequestParam String phone){
        try {
            cService.create(name, surname, dni, phone);
            model.put("exito", "El cliente fue registrado exitosamente!!");
            return "form-client";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "form-client";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String formModify(ModelMap model, @PathVariable String id){
        try {
            model.put("cliente", cService.searchById(id));
            return "modify-client";
        } catch (ExceptionService e) {
            return "modify-client";
        }
    }
    
    @PostMapping("/modificar/{id}")
    public String modify(ModelMap model, @PathVariable String id, @RequestParam String name, @RequestParam String surname, Long dni, @RequestParam String phone){
        try {
            cService.modify(id, name, surname, dni, phone);
            model.put("exito", "El/la cliente fue modificado/a exitosamente!!");
            model.put("cliente", cService.searchById(id));
            return "modify-client";
        } catch (ExceptionService e) {
            try {
                model.put("error", e.getMessage());
                model.put("cliente", cService.searchById(id));
                return "modify-client";
            } catch (ExceptionService ex) {
                return "redirect:/cliente/lista";
            }
        }
    }
    
    @GetMapping("/lista")
    public String list(ModelMap model) {
        try {
            model.addAttribute("clientes", cService.searchAll());
            return "list-client";
        } catch (ExceptionService e) {
            model.put("error", e.getMessage());
            return "list-client";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String drop(@PathVariable String id) {
        try {
            cService.drop(id);
            return "redirect:/cliente/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/alta/{id}")
    public String high(@PathVariable String id) {
        try {
            cService.high(id);
            return "redirect:/cliente/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
}
