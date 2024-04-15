package com.example.crudapp.controllers;

import com.example.crudapp.entities.Client;
import com.example.crudapp.entities.Registration;
import com.example.crudapp.entities.Workout;
import com.example.crudapp.repos.ClientRepository;
import com.example.crudapp.repos.RegistrationRepository;
import com.example.crudapp.repos.WorkoutRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClientController {
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public WorkoutRepository workoutRepository;
    @Autowired
    public RegistrationRepository registrationRepository;

    @GetMapping("/clients")
    public String clients(Model model) {
        List<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "clients_list";
    }

    @GetMapping("/clients/new")
    public String newClient(Model model) {
        model.addAttribute("clients", new Client());
        return "clients_new";
    }

    @PostMapping("/clients/new")
    public String storeClient(
            @Valid @ModelAttribute("clients") Client client,
            BindingResult bindingResult,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ){
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "clients_new";
        }
        Client c = new Client(name, surname, email, phone);
        clientRepository.save(c);
        return "redirect:/clients";
    }

    @GetMapping("/clients/update/{id}")
    public String newClient(
            @PathVariable("id") Integer clientID,
            Model model
    ) {
        Client c = clientRepository.getReferenceById(clientID);
        model.addAttribute("clients", c);
        return "clients_update";
    }

    @PostMapping("/clients/update/{id}")
    public String saveClient(
            @Valid Client client,
            BindingResult bindingResult,
            Model model,
            @PathVariable("id") Integer clientID,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ){
        if (bindingResult.hasErrors()) {
            model.addAttribute("clients", client);
            return "clients_new";
        }
        Client c=clientRepository.getReferenceById(clientID);
        c.setName(name);
        c.setSurname(surname);
        c.setEmail(email);
        c.setPhone(phone);
        clientRepository.save(c);

        return "redirect:/clients";
    }

    @PostMapping("/clients/delete/{id}")
    public  String deleteClient(
            @PathVariable("id") Integer clientID
    ){
        clientRepository.deleteById(clientID);
        return "redirect:/clients";
    }

    @GetMapping("/clients/info/{id}")
    public String getInfo(
            @PathVariable("id") Integer clientID,
            Model model
    ) {
        Client c = clientRepository.getReferenceById(clientID);
        List<Workout> workouts = workoutRepository.findAll();
        List<Registration> registrations = registrationRepository.findAll();
        model.addAttribute("registrations", registrations);
        model.addAttribute("workouts", workouts);
        model.addAttribute("clients", c);
        return "clients_info";
    }

}
