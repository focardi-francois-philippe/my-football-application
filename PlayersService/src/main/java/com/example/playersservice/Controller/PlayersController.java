package com.example.playersservice.Controller;

import com.example.playersservice.Model.Joueur;
import com.example.playersservice.Utils.Joueurs;
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
public class PlayersController {

    @Autowired
    RestTemplate restTemplate;
    ArrayList<Joueur> listJoueurs = new ArrayList<>();

    @GetMapping(value = "/players/create/{nombreJoueurs}")
    public List<Joueur> AllJoueurs(@PathVariable(required = false, name = "nombreJoueurs") int  nombreJoueurs)
    {

        return Joueurs.createJoueurs(nombreJoueurs);
    }

    @PostMapping(value = "/players/teams/{idEquipe}")
    public  Joueur addJoueur(@PathVariable int idEquipe)
    {
        Joueur j = Joueurs.createJoueurs(1).get(0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Joueur> entity = new HttpEntity<>(j,headers);
        String url = "http://127.0.0.1:3000/teams/"+idEquipe+"/addPlayers";
        restTemplate.exchange(url, HttpMethod.POST, entity,Joueur.class);
        return j;
    }
    @GetMapping(value = "/players/{id}")
    public Joueur joueurById(@PathVariable int id)
    {
        String url = "http://127.0.0.1:3000/teams/players/"+id;
        Joueur response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Joueur>() {}
        ).getBody();

        return  response;
    }

    @DeleteMapping("/players/{id}")
    public Joueur joueur(@PathVariable int id)
    {
        String url = "http://127.0.0.1:3000/teams/players/"+id;
        Joueur response = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Joueur>() {}
        ).getBody();

        return response;
    }

    @Bean
    public RestTemplate restTemplate()
    {
        return  new RestTemplate();
    }
}
