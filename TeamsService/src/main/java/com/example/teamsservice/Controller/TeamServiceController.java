package com.example.teamsservice.Controller;

import com.example.teamsservice.Model.Equipe;
import com.example.teamsservice.Model.Joueur;
import com.example.teamsservice.Utils.Joueurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;



@RestController
public class TeamServiceController {
    @Autowired
    public RestTemplate restTemplate;
    public static List<Equipe> equipeList;

    public TeamServiceController() {

        //createJoueurs(e,16);
        //createJoueurs(e1);
        //createJoueurs(e2);
        equipeList = new ArrayList<>();

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
                return equipe;
            }
        }
        return null;
    }
    @GetMapping("/teams/default")
    public List<Equipe> DefaultEquipeAndPlayers()
    {
        Equipe e = new Equipe("BASTIA");

        Equipe e2 = new Equipe("CORTE");

        getPlayers(e,16);
        getPlayers(e2,16);

        equipeList.add(e);
        equipeList.add(e2);
        return  allTeams();
    }
    public void getPlayers(Equipe e, int nbrJoueurs)
    {
        if (nbrJoueurs <=0)
        {
            nbrJoueurs = 16;
        }
        String url = "http://127.0.0.1:3031/players/create/"+nbrJoueurs;

        List<Joueur> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Joueur>>() {}
        ).getBody();


        e.joueurs =  response;
    }

    @PostMapping("/teams")
    public Equipe addTeam(@RequestBody String nom)
    {
        Equipe e = new Equipe(nom);
        getPlayers(e,16);
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
    @PostMapping(value = "teams/{idEquipe}/addPlayers")
    public Equipe AddPlayers(@PathVariable int idEquipe, @RequestBody Joueur player)
    {

        Equipe e = teamsById(idEquipe);

        if (e == null)
        {
            return addTeam("name");
        }
        e.joueurs.add(player);
        return  e;
    }

    @GetMapping("/teams/players/{playerId}")
    public Joueur joueurById(@PathVariable int playerId)
    {
        for (Equipe e: equipeList) {
            for (Joueur j: e.joueurs) {
                if (j.id == playerId)
                    return j;
            }
        }
        return  null;
    }

    public Equipe equipeByIdJoueur(int idJoueur)
    {
        for (Equipe e: equipeList) {
            for (Joueur j: e.joueurs) {
                if (j.id == idJoueur)
                    return e;
            }
        }
        return  null;
    }

    @DeleteMapping("/teams/players/{playerId}")
    public Joueur joueur(@PathVariable int playerId)
    {
        Joueur j =  joueurById(playerId);

        if (j == null)
            return  null;

        Equipe e = equipeByIdJoueur(j.id);
        e.joueurs.remove(j);
        return j;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
