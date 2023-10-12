package com.example.teamservice;


import Model.Equipe;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeamServiceController {


    RestTemplate re;
    public List<Equipe> equipeList;


    public TeamServiceController() {
        Equipe e = new Equipe("BASTIA");
        Equipe e1 = new Equipe("PARIS");
        Equipe e2 = new Equipe("CORTE");
        this.equipeList = new ArrayList<>();
        this.equipeList.add(e);
        this.equipeList.add(e1);
        this.equipeList.add(e2);
    }
    @GetMapping(value = "/teams")
    public List<Equipe> allTeams()
    {
        return equipeList;
    }
    @GetMapping(value = "/teams/{id}")
    public Equipe teamsById(@PathVariable int id)
    {
        for (Equipe equipe : equipeList) {
            if (equipe.id == id) {
                return equipe; // Retourne l'employé s'il est trouvé
            }
        }
        return null;

    }
    @PostMapping("/teams")
    public Equipe addTeam(@RequestBody String nom)
    {
        Equipe e = new Equipe(nom);

        equipeList.add(e);

        return teamsById(e.id);
    }
    @DeleteMapping(value = "/teams/{id}")
    public Equipe TeamById(@PathVariable int id)
    {
        Equipe e = teamsById(id);

        equipeList.remove(e);
        return  e;
    }
    @PutMapping(value = "teams/{id}")
    public Equipe UpdateName(@PathVariable int id, @RequestBody String name)
    {
        Equipe e = teamsById(id);

        if (e == null)
        {
            return addTeam(name);
        }

        e.nom = name;
        return  e;
    }
    @Bean
    RestTemplate restTemplate()
    {
        return  new RestTemplate();
    }
}
