package com.example.crudapp.controllers;

import com.example.crudapp.entities.Client;
import com.example.crudapp.entities.Registration;
import com.example.crudapp.entities.Workout;
import com.example.crudapp.repos.ClientRepository;
import com.example.crudapp.repos.RegistrationRepository;
import com.example.crudapp.repos.WorkoutRepository;
import com.example.crudapp.services.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ClientController {
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public WorkoutRepository workoutRepository;
    @Autowired
    public RegistrationRepository registrationRepository;
    @Autowired
    public FileStorageService fileStorageService;

    @GetMapping("/")
    public String index() {
        return "redirect:/clients";
    }

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
            @RequestParam("phone") String phone,
            @RequestParam("file") MultipartFile file
    ){
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "clients_new";
        }
        Client c = new Client(name, surname, email, phone);
        if (!file.isEmpty()) {
            c.setAgreement(file.getOriginalFilename());
            clientRepository.save(c);
            fileStorageService.store(file, c.getClientID().toString());
            return "redirect:/clients";
        }
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
            @RequestParam("phone") String phone,
            @RequestParam("file") MultipartFile file,
            @RequestParam("agreement") String agreement
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
        System.out.println(file);
        if (!file.isEmpty()) {
            c.setAgreement(file.getOriginalFilename());
            clientRepository.save(c);
            fileStorageService.store(file, clientID.toString());
            return "redirect:/clients";
        }
        c.setAgreement(agreement);
        clientRepository.save(c);

        return "redirect:/clients";
    }

    @PostMapping("/clients/delete/{id}")
    public  String deleteClient(
            @PathVariable("id") Integer clientID
    ){
        clientRepository.deleteById(clientID);
        fileStorageService.delete(clientID.toString());
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

    @GetMapping("/clients/{id}/agreement")
    @ResponseBody
    public ResponseEntity<Resource> agreement(@PathVariable Integer id) {
        Client c = clientRepository.getReferenceById(id);
        Resource file = fileStorageService.load(c.getClientID().toString());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+c.getAgreement()+"\"")
                .body(file);
    }

}
