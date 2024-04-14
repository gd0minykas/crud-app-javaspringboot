package com.example.crudapp.controllers;

import org.springframework.ui.Model;
import com.example.crudapp.entities.Workout;
import com.example.crudapp.repos.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WorkoutController {
    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/workouts")
    public String workouts(Model model) {
        List<Workout> workouts = workoutRepository.findAll();
        model.addAttribute("workouts", workouts);
        return "workouts_list";
    }

    @GetMapping("/workouts/new")
    public String newWorkout() {
        return "workouts_new";
    }

    @PostMapping("/workouts/new")
    public String storeWorkout(
            @RequestParam("name") String name,
            @RequestParam("date") LocalDateTime date,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location
    ){
        Workout w = new Workout(name, date, places, location);
        workoutRepository.save(w);
        return "redirect:/workouts";
    }

    @GetMapping("/workouts/update/{id}")
    public String newWorkout(
            @PathVariable("id") Integer workoutID,
            Model model
    ) {
        Workout w = workoutRepository.getReferenceById(workoutID);
        model.addAttribute("workouts", w);
        return "workouts_update";
    }

    @PostMapping("/workouts/update/{id}")
    public String saveWorkout(
            @PathVariable("id") Integer workoutID,
            @RequestParam("name") String name,
            @RequestParam("date") LocalDateTime date,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location
    ){
        Workout w = workoutRepository.getReferenceById(workoutID);
        w.setName(name);
        w.setDate(date);
        w.setPlaces(places);
        w.setLocation(location);
        workoutRepository.save(w);

        return "redirect:/workouts";
    }

    @PostMapping("/workouts/delete/{id}")
    public  String deleteWorkout(
            @PathVariable("id") Integer workoutID
    ){
        workoutRepository.deleteById(workoutID);
        return "redirect:/workouts";
    }
}
