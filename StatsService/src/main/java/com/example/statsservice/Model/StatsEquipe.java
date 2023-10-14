package com.example.statsservice.Model;

public class StatsEquipe {
    public Equipe equipe;

    public int nombreDeButMarque;

    public int nombreDeButEncaisse;



    public StatsEquipe(Equipe equipe, int nombreDeButMarque, int nombreDeButEncaisse) {
        this.equipe = equipe;
        this.nombreDeButMarque = nombreDeButMarque;
        this.nombreDeButEncaisse = nombreDeButEncaisse;
    }
}
