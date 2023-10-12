package com.example.teamsservice.Model;

public class Joueur {
    public int id;
    public String nom;

    public static int ai_id = 1;

    public Joueur(String nom) {
        this.nom = nom;
        id = ai_id;
        ai_id++;
    }

    public Joueur() {
    }
}
