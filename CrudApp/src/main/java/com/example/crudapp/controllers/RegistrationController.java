package com.example.crudapp.controllers;

import com.example.crudapp.entities.Client;
import com.example.crudapp.entities.Registration;
import com.example.crudapp.entities.Workout;
import com.example.crudapp.repos.ClientRepository;
import com.example.crudapp.repos.RegistrationRepository;
import com.example.crudapp.repos.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private WorkoutRepository workoutRepository;


    @GetMapping("/registrations")
    public String registrations(Model model) {
        List<Registration> registrations = registrationRepository.findAll();
        model.addAttribute("registrations", registrations);
        return "registrations_list";
    }

    @GetMapping("/registrations/new")
    public String newRegistration(Model model) {
        List<Client> clients = clientRepository.findAll();
        List<Workout> workouts = workoutRepository.findAll();
        model.addAttribute("workouts", workouts);
        model.addAttribute("clients", clients);
        return "registrations_new";
    }

    @PostMapping("/registrations/new")
    public String storeRegistration(
            @RequestParam("client") Client client,
            @RequestParam("workout") Workout workout,
            @RequestParam("date") LocalDateTime date
    ){
        LocalDateTime dateMin = date.truncatedTo(ChronoUnit.MINUTES);
        Registration r = new Registration(client, workout, dateMin);
        registrationRepository.save(r);
        return "redirect:/registrations";
    }

    @GetMapping("/registrations/update/{id}")
    public String newRegistration(
            @PathVariable("id") Integer regID,
            Model model
    ) {
        List<Client> clients = clientRepository.findAll();
        List<Workout> workouts = workoutRepository.findAll();
        Registration r = registrationRepository.getReferenceById(regID);
        model.addAttribute("registrations", r);
        model.addAttribute("workouts", workouts);
        model.addAttribute("clients", clients);
        return "registrations_update";
    }

    @PostMapping("/registrations/update/{id}")
    public String saveRegistration(
            @PathVariable("id") Integer regID,
            @RequestParam("client") Client client,
            @RequestParam("workout") Workout workout,
            @RequestParam("date") LocalDateTime date
    ){
        Registration r = registrationRepository.getReferenceById(regID);
        r.setClient(client);
        r.setWorkout(workout);
        r.setDate(date);
        registrationRepository.save(r);

        return "redirect:/registrations";
    }

    @PostMapping("/registrations/delete/{id}")
    public  String deleteRegistration(
            @PathVariable("id") Integer regID,
            @RequestParam("client") Integer clientID
    ){
        registrationRepository.deleteById(regID);
        return "redirect:/clients/info/" + clientID;
    }

    @GetMapping("/registrations/new/{id}")
    public String newClientRegistration(
            @PathVariable("id") Integer clientID,
            Model model
    ) {
        Client c = clientRepository.getReferenceById(clientID);
        List<Workout> workouts = workoutRepository.findAll();
        model.addAttribute("workouts", workouts);
        model.addAttribute("clients", c);
        return "registrations_new_info";
    }

    @PostMapping("/registrations/new/{id}")
    public String storeRegistration(
            @PathVariable("id") Integer clientID,
            @RequestParam("workout") Workout workout,
            @RequestParam("date") LocalDateTime date
    ){
        Client c = clientRepository.getReferenceById(clientID);
        LocalDateTime dateMin = date.truncatedTo(ChronoUnit.MINUTES);
        Registration r = new Registration(c, workout, dateMin);
        registrationRepository.save(r);

        return "redirect:/clients/info/" + clientID;
    }
}
