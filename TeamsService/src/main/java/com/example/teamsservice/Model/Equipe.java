package com.example.teamsservice.Model;

import java.util.ArrayList;
import java.util.List;

public class Equipe {
    public String nom;

    public Integer id;

    public List<Joueur> joueurs;

    public static int identifiantVal = 1;

    public Equipe(String nom) {
        this.nom = nom;
        this.id = identifiantVal;
        this.joueurs = new ArrayList<>();
        identifiantVal++;
    }
}