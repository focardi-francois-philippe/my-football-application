package com.example.playersservice.Controller;

import com.example.playersservice.Model.Joueur;
import com.example.playersservice.Utils.Joueurs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayersController {

    ArrayList<Joueur> listJoueurs = new ArrayList<>();

    @GetMapping(value = "/players/create/{nombreJoueurs}")
    public List<Joueur> AllJoueurs(@PathVariable(required = false, name = "nombreJoueurs") int  nombreJoueurs)
    {

        return Joueurs.createJoueurs(nombreJoueurs);
    }

    @PostMapping(value = "players")
    public  Joueur addJoueur()
    {
        return Joueurs.createJoueurs(1).get(0);
    }
    @GetMapping(value = "/players/{id}")
    public Joueur teamsById(@PathVariable int id)
    {
        for (Joueur joueur : listJoueurs) {
            if (joueur.id == id) {
                return joueur; // Retourne l'employé s'il est trouvé
            }
        }
        return null;

    }
}
