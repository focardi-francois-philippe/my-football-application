package com.example.matchservice.Model;


import java.util.ArrayList;
import java.util.List;

public class Equipe {

    public String nom;

    public Integer id;

    public List<Joueur> joueurs;

    public static int identifiantVal = 1;

    public Equipe(String nom) {
        this();
        this.nom = nom;

    }

    public Equipe() {
       this.id = identifiantVal;
        this.joueurs = new ArrayList<Joueur>();
        identifiantVal++;
    }

    public Equipe(String nom, ArrayList<Joueur> joueurs) {
        this.nom = nom;
        this.joueurs = joueurs;
    }
}